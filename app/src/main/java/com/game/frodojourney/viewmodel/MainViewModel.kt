package com.game.frodojourney.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.frodojourney.app.canvas.Coordinate
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.app.character.LukeRun
import com.game.frodojourney.app.character.PixelMainCharacter
import com.game.frodojourney.app.map.Corusant
import com.game.frodojourney.app.map.GameMap
import com.game.frodojourney.character.CharacterTurned
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MapState(
    val map: GameMap = Corusant()
)

const val borderToMap = 30f
const val borderToMapTwoTimes = 60f

@Stable
data class MainViewModel(
    private val _mapState: MutableStateFlow<MapState> = MutableStateFlow(MapState()),
    val mapState: StateFlow<MapState> = _mapState.asStateFlow(),
    private val _viewData: MutableStateFlow<ViewData> = MutableStateFlow(ViewData()),
    val viewData: StateFlow<ViewData> = _viewData.asStateFlow(),
    private val _character: MutableStateFlow<PixelMainCharacter> = MutableStateFlow(
        PixelMainCharacter()
    ),
    val character: StateFlow<PixelMainCharacter> = _character.asStateFlow(),
    private var movementJob: Job? = null,
    private var animationJob: Job? = null,
) : ViewModel() {

    private fun setFight(fighting: Boolean) {
        _character.value = _character.value.copyWeaponAware(isFighting = fighting)
    }

    fun fightWithLightSaber() {
        viewModelScope.launch {
            setFrame(LukeRun.removeWeapon())
            setFight(true)
            for (i in (1..15)) {
                val prevWeapon = _character.value.weapon
                _character.value =
                    _character.value.copyWeaponAware(weapon = prevWeapon.copy(rotation = i * 24f))
                awaitFrame()
            }
            setFight(false)
            setFrame(LukeRun.reset())
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


    fun setFrame(frame: ImageBitmap) {
        _character.value = _character.value.copyWeaponAware(characterFrame = frame)
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

    fun updateCharacterPosX(delta: Dp) {
        if (isInAllowedAreaX(delta)) {
            with(_viewData.value) {
                val characterPositionAsOffset = _character.value.position.toOffset()
                with(_viewData.value.density) {
                    val characterYDp = characterPositionAsOffset.x.toDp()
                    if (characterYDp + delta in (borderToMap.dp..(_viewData.value.size.width.toDp() - borderToMapTwoTimes.dp))) {
                        changePositionX(delta)
                    } else if (xWouldBeInBounds(delta)) {
                        changePositionX(delta)
                        updateViewDataX(delta)
                    }
                }
            }
        }
    }

    private fun xWouldBeInBounds(delta: Dp): Boolean {
        with(_viewData.value.density) {
            val halfSize = (_viewData.value.size.width / 2)
            return _viewData.value.focus.x + delta.toPx() in halfSize.._mapState.value.map.mapImage.width.toFloat() - halfSize
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

    private fun yWouldBeInBounds(delta: Dp): Boolean {
        with(_viewData.value.density) {
            val halfSize = (_viewData.value.size.height / 2)
            return _viewData.value.focus.y + delta.toPx() in halfSize.._mapState.value.map.mapImage.height.toFloat() - halfSize
        }
    }

    fun updateCharacterPosY(delta: Dp) {
        if (isInAllowedAreaY(delta)) {
            with(_viewData.value) {
                val characterPositionAsOffset = _character.value.position.toOffset()
                with(_viewData.value.density) {
                    val characterYDp = characterPositionAsOffset.y.toDp()
                    if (characterYDp + delta in (borderToMap.dp..(_viewData.value.size.height.toDp() - borderToMapTwoTimes.dp))) {
                        changePositionY(delta)
                    } else if (yWouldBeInBounds(delta)) {
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

    fun turnCharacter(turn: CharacterTurned) {
        _character.value = _character.value.copyWeaponAware(turned = turn)
    }
}

