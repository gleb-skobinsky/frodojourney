package com.game.frodojourney.viewmodel

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.game.frodojourney.character.CharacterTurned
import com.game.frodojourney.character.Frodo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _playableField: MutableStateFlow<DpSize> = MutableStateFlow(DpSize.Zero)
    fun setPlayableField(size: DpSize) {
        _playableField.value = size
    }

    private val _character: MutableStateFlow<Frodo> =
        MutableStateFlow(Frodo())
    val character: StateFlow<Frodo> = _character
    fun setCharacter(newCharacter: Frodo) {
        _character.value = newCharacter
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