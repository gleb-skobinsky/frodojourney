package com.game.darkforce.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.darkforce.app.canvas.Coordinate
import com.game.darkforce.app.canvas.Coordinates
import com.game.darkforce.app.canvas.DpCoordinates
import com.game.darkforce.app.canvas.ViewData
import com.game.darkforce.app.canvas.calculateAngle
import com.game.darkforce.app.canvas.distance
import com.game.darkforce.app.character.CharacterTurned
import com.game.darkforce.app.character.enemies.ImperialSquad
import com.game.darkforce.app.character.enemies.Trooper
import com.game.darkforce.app.character.enemies.TrooperAim
import com.game.darkforce.app.character.enemies.TrooperDying
import com.game.darkforce.app.character.enemies.hashSquad
import com.game.darkforce.app.character.mainCharacter.Luke
import com.game.darkforce.app.character.mainCharacter.LukeRun
import com.game.darkforce.app.character.mainCharacter.MainHero
import com.game.darkforce.app.character.yoda.Yoda
import com.game.darkforce.app.map.Corusant
import com.game.darkforce.app.map.GameMap
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MapState(
    val map: GameMap = Corusant()
)

const val borderToScreen = 30f
const val borderToScreenTwoTimes = 60f
const val awarenessBorder = 300f
const val killBorder = 50f
const val borderToYoda = 100

@Stable
data class MainViewModel(
    private val _mapState: MutableStateFlow<MapState> = MutableStateFlow(MapState()),
    val mapState: StateFlow<MapState> = _mapState.asStateFlow(),
    private val _viewData: MutableStateFlow<ViewData> = MutableStateFlow(ViewData()),
    val viewData: StateFlow<ViewData> = _viewData.asStateFlow(),
    private val _character: MutableStateFlow<MainHero> = MutableStateFlow(MainHero()),
    val character: StateFlow<MainHero> = _character.asStateFlow(),
    val squad: ImperialSquad = hashSquad,
    private val _hp: MutableStateFlow<Int> = MutableStateFlow(300),
    val hp: StateFlow<Int> = _hp.asStateFlow(),
    private val _yoda: MutableStateFlow<Yoda?> = MutableStateFlow(null),
    val yoda: StateFlow<Yoda?> = _yoda.asStateFlow(),
    private val _overlayOpen: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val overlayOpen: StateFlow<Boolean> = _overlayOpen.asStateFlow(),
    private var movementJob: Job? = null,
    private var animationJob: Job? = null,
    private val _currentQuestion: MutableStateFlow<Int> = MutableStateFlow(-1),
    val currentQuestion: StateFlow<Int> = _currentQuestion.asStateFlow()
) : ViewModel() {

    fun incrementQuestion() {
        _currentQuestion.update { it + 1 }
    }

    private fun createYoda() {
        _yoda.value = Yoda()
        viewModelScope.launch {
            for (i in 0..100) {
                val oldYoda = _yoda.value
                _yoda.value =
                    oldYoda?.copy(position = oldYoda.position.copy(x = oldYoda.position.x - 1.dp))
                awaitFrame()
            }
        }
    }

    fun minusHp() {
        if (_hp.value > 0) {
            _hp.value--
        }
    }

    private fun turnAndAim(trooper: Trooper): Triple<CharacterTurned, TrooperAim, Float> {
        val angle = calculateAngle(
            coordinate1 = trooper.center,
            coordinate2 = _character.value.center
        )
        val (turn, aim) = calculateTurnAndAim(angle)
        return Triple(turn, aim, angle)
    }

    private fun troopersFollowTarget() {
        for (entry in squad.entries) {
            if (!entry.value.isDying) {
                val (turn, aim, angle) = turnAndAim(entry.value)
                val trooper = entry.value.copy(
                    turned = turn,
                    aiming = aim,
                    image = aim.toImage(),
                    aimingDirection = angle
                )
                squad[entry.key] = trooper
            }
        }
    }


    private fun checkTroopersDistance() {
        for (entry in squad.entries) {
            if (!entry.value.isDying) {
                val pos1 = entry.value.position
                val pos2 = _character.value.position
                if (distance(pos1, pos2) < awarenessBorder) {
                    triggerTrooper(entry)
                } else {
                    calmTrooper(entry)
                }
            }
        }
    }

    private fun calmTrooper(entry: Map.Entry<String, Trooper>) {
        val trooper = entry.value.copy(isAlarmed = false)
        squad[entry.key] = trooper
    }

    private fun triggerTrooper(entry: Map.Entry<String, Trooper>) {
        val trooper = entry.value.copy(isAlarmed = true)
        squad[entry.key] = trooper
    }

    private fun setFight(fighting: Boolean) {
        _character.value = _character.value.copyWeaponAware(isFighting = fighting)
    }


    fun setTrooper1(trooperId: String, trooper: Trooper) {
        squad[trooperId] = trooper
    }

    private fun setSaberRotation(value: Float) {
        val prevWeapon = _character.value.weapon
        _character.value =
            _character.value.copyWeaponAware(weapon = prevWeapon.copy(rotation = value))
    }

    private fun killTrooper(trooperId: String, trooper: Trooper) {
        viewModelScope.launch {
            for (image in TrooperDying.images) {
                val updated = trooper.copy(image = image)
                squad[trooperId] = updated
                delay(100L)
            }
            squad.remove(trooperId)
            if (squad.isEmpty()) createYoda()
        }
    }


    fun fightWithLightSaber() {
        viewModelScope.launch {
            setFrame(Luke.removeWeapon())
            setFight(true)
            for (i in (1..30)) {
                setSaberRotation(i * 12f)
                if (i == 15) {
                    attemptKillTrooper()
                }
                awaitFrame()
            }
            setSaberRotation(0f)
            setFight(false)
            setFrame(LukeRun.reset())
        }
    }

    private fun attemptKillTrooper() {
        val killedTrooper = squad.entries.firstOrNull {
            distance(
                it.value.position,
                character.value.position
            ) < killBorder
        }
        killedTrooper?.let { (id, trooper) ->
            if (!trooper.isDying) {
                val newTrooper = trooper.copy(isDying = true)
                squad[id] = newTrooper
                killTrooper(id, newTrooper)
            }
        }
    }

    private fun launchMovementCoroutine() {
        movementJob = viewModelScope.launch {
            while (_character.value.isMoving && !_character.value.isFighting) {
                turnCharacter(_character.value.turned)
                updateCharacterPosX(_character.value.stepX)
                updateCharacterPosY(_character.value.stepY)
                awaitFrame()
            }
        }
        animationJob = viewModelScope.launch {
            while (_character.value.isMoving && !_character.value.isFighting) {
                setFrame(LukeRun.next())
                delay(100L)
            }
            setFrame(LukeRun.reset())
        }
    }

    fun startMovement(turn: CharacterTurned, stepX: Dp, stepY: Dp) {
        _character.value =
            _character.value.copyWeaponAware(
                isMoving = true,
                turned = turn,
                stepX = stepX,
                stepY = stepY
            )
        if (movementJob == null && animationJob == null) {
            launchMovementCoroutine()
        }

        troopersFollowTarget()
        checkTroopersDistance()
        yoda.value?.let {
            if (distance(character.value.position, it.position) < borderToYoda) {
                _overlayOpen.value = true
            }
        }
    }

    fun stopMovement() {
        _character.value = _character.value.copyWeaponAware(isMoving = false)
        movementJob = null
        animationJob = null
    }

    fun updateOrientation(newOrientation: Int) {
        if (_viewData.value.orientation != newOrientation) {
            _viewData.value = _viewData.value.copy(focus = Coordinates(xOverBound(), yOverBound()))
        }
        _viewData.value = _viewData.value.copy(orientation = newOrientation)
    }

    fun setViewData(viewData: ViewData) {
        _viewData.value = viewData
    }


    private fun setFrame(frame: ImageBitmap) {
        _character.value = _character.value.copyWeaponAware(image = frame)
    }

    private fun isInAllowedAreaX(delta: Dp): Boolean {
        val prevPos = _character.value.position
        val supposedPosX = prevPos.x + delta
        val newPos = DpCoordinates(supposedPosX, prevPos.y)
        return newPos in _mapState.value.map.allowedArea
    }

    private fun isInAllowedAreaY(delta: Dp): Boolean {
        val prevPos = _character.value.position
        val supposedPosY = prevPos.y + delta
        val newPos = DpCoordinates(prevPos.x, supposedPosY)
        return newPos in _mapState.value.map.allowedArea
    }

    private fun updateCharacterPosX(delta: Dp) {
        with(_viewData.value) {
            val characterPositionAsOffset = _character.value.position.toOffset()
            with(_viewData.value.density) {
                val characterYDp = characterPositionAsOffset.x.toDp()
                if (isInAllowedAreaX(delta)) {
                    if (characterYDp + delta in (borderToScreen.dp..(_viewData.value.size.width.toDp() - borderToScreenTwoTimes.dp))) {
                        changePositionX(delta)
                    } else {
                        changePositionX(delta)
                        updateViewDataX(delta)
                    }
                }
            }
        }

    }

    private fun xOverBound(): Coordinate {
        with(_viewData.value.density) {
            val halfSize = (_viewData.value.size.width / 2)
            val endOfAvailable = _mapState.value.map.mapImage.width.toFloat() - halfSize
            var pos = _character.value.position.x.toPx()
            when {
                pos < halfSize -> pos = halfSize
                pos > endOfAvailable -> pos = endOfAvailable
            }
            return pos
        }
    }

    private fun yOverBound(): Coordinate {
        with(_viewData.value.density) {
            val halfSize = (_viewData.value.size.height / 2)
            val endOfAvailable = _mapState.value.map.mapImage.height.toFloat() - halfSize
            var pos = _character.value.position.y.toPx()
            when {
                pos < halfSize -> pos = halfSize
                pos > endOfAvailable -> pos = endOfAvailable
            }
            return pos
        }
    }

    private fun updateCharacterPosY(delta: Dp) {
        with(_viewData.value) {
            val characterPositionAsOffset = _character.value.position.toOffset()
            with(_viewData.value.density) {
                val characterYDp = characterPositionAsOffset.y.toDp()
                if (isInAllowedAreaY(delta)) {
                    if (characterYDp + delta in (borderToScreen.dp..(_viewData.value.size.height.toDp() - borderToScreenTwoTimes.dp))) {
                        changePositionY(delta)
                    } else {
                        changePositionY(delta)
                        updateViewDataY(delta)
                    }
                }
            }
        }
    }

    private fun updateViewDataX(delta: Dp) {
        with(_viewData.value.density) {
            val prevOffset = _viewData.value.focus
            _viewData.value =
                _viewData.value.copy(focus = prevOffset.copy(x = prevOffset.x + delta.toPx()))
        }
    }

    private fun updateViewDataY(delta: Dp) {
        with(_viewData.value.density) {
            val prevOffset = _viewData.value.focus
            _viewData.value =
                _viewData.value.copy(focus = prevOffset.copy(y = prevOffset.y + delta.toPx()))
        }
    }

    private fun changePositionY(delta: Dp) {
        val prevPos = _character.value.position
        _character.value =
            _character.value.copyWeaponAware(position = prevPos.copy(y = prevPos.y + delta))
    }

    private fun changePositionX(delta: Dp) {
        val prevPos = _character.value.position
        _character.value =
            _character.value.copyWeaponAware(position = prevPos.copy(x = prevPos.x + delta))
    }

    private fun turnCharacter(turn: CharacterTurned) {
        _character.value = _character.value.copyWeaponAware(turned = turn)
    }
}

private fun calculateTurnAndAim(value: Float): Pair<CharacterTurned, TrooperAim> {
    val turn = when (value) {
        in 90f..270f -> CharacterTurned.RIGHT
        else -> CharacterTurned.LEFT
    }
    val aim = when (value) {
        in 0f..22.5f -> TrooperAim.SIDE
        in 22.5f..67.5f -> TrooperAim.DOWNSIDE
        in 67.5f..112.5f -> TrooperAim.DOWN
        in 112.5f..157.5f -> TrooperAim.DOWNSIDE
        in 157.5f..202.5f -> TrooperAim.SIDE
        in 202.5f..247.5f -> TrooperAim.UPSIDE
        in 247.5f..292.5f -> TrooperAim.UP
        in 292.5f..337.5f -> TrooperAim.UPSIDE
        in 337.5f..360f -> TrooperAim.SIDE
        else -> TrooperAim.SIDE
    }
    return Pair(turn, aim)
}
