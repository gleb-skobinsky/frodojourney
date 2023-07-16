package com.game.frodojourney.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.game.frodojourney.app.character.PixelMainCharacter
import com.game.frodojourney.app.map.Corusant
import com.game.frodojourney.app.map.GameMap
import com.game.frodojourney.app.toDpOffset
import com.game.frodojourney.character.CharacterTurned
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MapState(
    val map: GameMap = Corusant()
)

typealias Coordinate = Float

data class ViewData(
    val focus: Coordinates = Coordinates(750f, 1550f),
    val size: Size = Size(1f, 1f)
) {
    fun Coordinates.toOffset() = Offset(
        x = (x - focus.x) + size.width / 2,
        y = (y - focus.y) + size.height / 2
    )
}

const val borderToMap = 30
const val borderToMapTwoTimes = 60

data class MainViewModel(
    var localDensity: Density = Density(1f),
    var configuration: Int = 1,
    private val _mapState: MutableStateFlow<MapState> = MutableStateFlow(MapState()),
    val mapState: MutableStateFlow<MapState> = _mapState,
    private val _playableField: MutableStateFlow<DpSize> = MutableStateFlow(DpSize.Zero),
    private val _character: MutableStateFlow<PixelMainCharacter> =
        MutableStateFlow(PixelMainCharacter(_mapState.value.map.characterInitialPosition)),
    val character: StateFlow<PixelMainCharacter> = _character,
    private val _characterFrame: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null),
    val characterFrame: StateFlow<ImageBitmap?> = _characterFrame,

    private val _viewData: MutableStateFlow<ViewData> = MutableStateFlow(ViewData()),
    val viewData: StateFlow<ViewData> = _viewData
) : ViewModel() {

    fun resizeMap(size: Size) {
        _viewData.value = _viewData.value.copy(size = size)
    }

    fun setPlayableField(size: DpSize, localConfiguration: Int) {
        _playableField.value = size
        configuration = localConfiguration
    }

    fun setCharacter(character: PixelMainCharacter) {
        _character.value = character
    }


    fun setFrame(frame: ImageBitmap) {
        _characterFrame.value = frame
    }

    fun updateCharacterPosX(delta: Dp) {
        with(_viewData.value) {
            val characterPositionAsOffset = _character.value.position.toOffset()
            with(localDensity) {
                val characterXDp = characterPositionAsOffset.x.toDp()
                if (characterXDp + delta in (borderToMap.dp..(_playableField.value.width - borderToMapTwoTimes.dp))) {
                    changePositionX(delta)
                } else {
                    changePositionX(delta)
                    updateViewDataX(delta)
                }
            }
        }
    }

    fun updateCharacterPosY(delta: Dp) {
        with(_viewData.value) {
            val characterPositionAsOffset = _character.value.position.toOffset()
            with(localDensity) {
                val characterYDp = characterPositionAsOffset.y.toDp()
                if (characterYDp + delta in (borderToMap.dp..(_playableField.value.height - borderToMapTwoTimes.dp))) {
                    changePositionY(delta)
                } else {
                    changePositionY(delta)
                    updateViewDataY(delta)
                }
            }
        }
    }

    private fun updateViewDataX(delta: Dp) {
        val prevOffset = _viewData.value.focus
        _viewData.value =
            _viewData.value.copy(focus = prevOffset.copy(x = prevOffset.x + delta.value))
    }

    private fun updateViewDataY(delta: Dp) {
        val prevOffset = _viewData.value.focus
        _viewData.value =
            _viewData.value.copy(focus = prevOffset.copy(y = prevOffset.y + delta.value))
    }

    private fun yIsAtEdge(characterPos: Dp, delta: Dp): Boolean {
        with(_viewData.value) {
            with(localDensity) {
                val focusDp = _viewData.value.focus.toOffset().toDpOffset(localDensity).y
                return characterPos + delta <= 0.dp || characterPos + delta >= focusDp
            }
        }
    }

    private fun changePositionY(delta: Dp) {
        val prevPos = _character.value.position
        _character.value =
            _character.value.copy(position = prevPos.copy(y = prevPos.y + delta.value))
    }

    private fun changePositionX(delta: Dp) {
        val prevPos = _character.value.position
        _character.value =
            _character.value.copy(position = prevPos.copy(x = prevPos.x + delta.value))
    }

    fun turnCharacter(turn: CharacterTurned) {
        _character.value = _character.value.copy(turned = turn)
    }
}

data class Coordinates(
    val x: Coordinate,
    val y: Coordinate
) {
    companion object {
        val Zero = Coordinates(0f, 0f)
    }
}