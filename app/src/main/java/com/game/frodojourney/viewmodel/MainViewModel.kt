package com.game.frodojourney.viewmodel

import android.content.res.Configuration.ORIENTATION_UNDEFINED
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.app.character.PixelMainCharacter
import com.game.frodojourney.app.map.Corusant
import com.game.frodojourney.app.map.GameMap
import com.game.frodojourney.character.CharacterTurned
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MapState(
    val map: GameMap = Corusant()
)

const val borderToMap = 30f
const val borderToMapTwoTimes = 60f

data class MainViewModel(
    private val _mapState: MutableStateFlow<MapState> = MutableStateFlow(MapState()),
    val mapState: MutableStateFlow<MapState> = _mapState,
    private val _characterFrame: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null),
    val characterFrame: StateFlow<ImageBitmap?> = _characterFrame,
    private val _viewData: MutableStateFlow<ViewData> = MutableStateFlow(ViewData()),
    val viewData: StateFlow<ViewData> = _viewData,
    private val _character: MutableStateFlow<PixelMainCharacter> = MutableStateFlow(
        PixelMainCharacter()
    ),
    val character: StateFlow<PixelMainCharacter> = _character,
) : ViewModel() {

    fun updateOrientation(orientation: Int) {
        val prev = _viewData.value.orientation
        _viewData.value = _viewData.value.copy(orientation = orientation)
        if (prev != ORIENTATION_UNDEFINED && prev != orientation) {
            _viewData.value = _viewData.value.copy(focus = _character.value.position)
        }
    }

    fun setViewData(viewData: ViewData) {
        _viewData.value = viewData
    }


    fun setFrame(frame: ImageBitmap) {
        _characterFrame.value = frame
    }

    fun setInitialCharacterPosition(position: Coordinates) {
        _character.value = _character.value.copy(position = position)
    }

    fun updateCharacterPosX(delta: Dp) {
        with(_viewData.value) {
            val characterPositionAsOffset = _character.value.position.toOffset()
            with(_viewData.value.density) {
                val characterXDp = characterPositionAsOffset.x.toDp()
                if (characterXDp + delta in (borderToMap.dp..(_viewData.value.size.width.toDp() - borderToMapTwoTimes.dp))) {
                    changePositionX(delta)
                } else if (xWouldBeInBounds(delta)) {
                    changePositionX(delta)
                    updateViewDataX(delta)
                }
            }
        }
    }

    private fun xWouldBeInBounds(delta: Dp): Boolean {
        val halfSize = (_viewData.value.size.width / 2)
        return _viewData.value.focus.x + delta.value in halfSize.._mapState.value.map.mapImage.width.toFloat() - halfSize
    }

    private fun yWouldBeInBounds(delta: Dp): Boolean {
        val halfSize = (_viewData.value.size.height / 2)
        return _viewData.value.focus.y + delta.value in halfSize.._mapState.value.map.mapImage.height.toFloat() - halfSize
    }

    fun updateCharacterPosY(delta: Dp) {
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

