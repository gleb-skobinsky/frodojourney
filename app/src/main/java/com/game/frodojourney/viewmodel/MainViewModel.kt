package com.game.frodojourney.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.game.frodojourney.app.character.PixelMainCharacter
import com.game.frodojourney.character.CharacterTurned
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MainViewModel(
    private val _playableField: MutableStateFlow<DpSize> = MutableStateFlow(DpSize.Zero),
    private val _character: MutableStateFlow<PixelMainCharacter> =
        MutableStateFlow(PixelMainCharacter()),
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
        val pos = _character.value.position
        val newPos = pos.x + delta
        if (newPos in (0.dp.._playableField.value.width)) {
            _character.value = _character.value.copy(position = pos.copy(x = newPos))
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