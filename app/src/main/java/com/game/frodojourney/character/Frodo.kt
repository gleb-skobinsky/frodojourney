package com.game.frodojourney.character

import androidx.compose.ui.unit.DpOffset

data class Frodo(
    val position: DpOffset = DpOffset.Zero,
    val turned: CharacterTurned = CharacterTurned.RIGHT
)

enum class CharacterTurned(val mirrorX: Float) {
    LEFT(1f),
    RIGHT(-1f)
}