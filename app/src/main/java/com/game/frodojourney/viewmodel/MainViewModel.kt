package com.game.frodojourney.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.game.frodojourney.app.character.PixelMainCharacter
import com.game.frodojourney.app.map.Corusant
import com.game.frodojourney.app.map.GenericMap
import com.game.frodojourney.character.CharacterTurned
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MapState(
    val map: GenericMap = Corusant(),
    val viewPortOffset: DpOffset = map.startingPosition,
)

data class MainViewModel(
    var localDensity: Density = Density(1f),
    private val _mapState: MutableStateFlow<MapState> = MutableStateFlow(MapState()),
    val mapState: MutableStateFlow<MapState> = _mapState,
    private val _playableField: MutableStateFlow<DpSize> = MutableStateFlow(DpSize.Zero),
    private val _character: MutableStateFlow<PixelMainCharacter> =
        MutableStateFlow(PixelMainCharacter(_mapState.value.map.characterInitialPosition)),
    val character: StateFlow<PixelMainCharacter> = _character,
    private val _characterFrame: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null),
    val characterFrame: StateFlow<ImageBitmap?> = _characterFrame,
) : ViewModel() {

    fun setPlayableField(size: DpSize) {
        _playableField.value = size
    }

    fun setCharacter(character: PixelMainCharacter) {
        _character.value = character
    }


    fun setFrame(frame: ImageBitmap) {
        _characterFrame.value = frame
    }

    fun updateCharacterPosX(delta: Dp) {
        with(localDensity) {
            val pos = _character.value.position
            val newPos = pos.x + delta
            if (newPos in (30.dp..(_playableField.value.width - 60.dp))) {
                _character.value = _character.value.copy(position = pos.copy(x = newPos))
            } else if (isAtEdge(delta, this)) {
                val prevOffset = _mapState.value.viewPortOffset
                _mapState.value =
                    _mapState.value.copy(viewPortOffset = prevOffset.copy(x = prevOffset.x - delta))
            }
        }
    }

    private fun isAtEdge(delta: Dp, density: Density): Boolean {
        with(density) {
            val viewPortStart = _mapState.value.viewPortOffset.x
            val viewPortEnd = viewPortStart - _playableField.value.width
            val mapEnd = _mapState.value.map.mapImage.width.toDp()
            return viewPortStart - delta <= 0.dp && viewPortEnd - delta >= -mapEnd
        }
    }

    fun updateCharacterPosY(delta: Dp) {
        val pos = _character.value.position
        val newPos = pos.y + delta
        if (newPos in (0.dp.._playableField.value.height)) {
            _character.value = _character.value.copy(position = pos.copy(y = newPos))
        }
    }

    fun turnCharacter(turn: CharacterTurned) {
        _character.value = _character.value.copy(turned = turn)
    }
}