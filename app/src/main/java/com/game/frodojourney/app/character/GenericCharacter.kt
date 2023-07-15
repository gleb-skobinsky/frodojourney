package com.game.frodojourney.app.character


import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.DpOffset
import com.game.frodojourney.character.CharacterTurned

interface GenericCharacter {
    val position: DpOffset
    val turned: CharacterTurned

    fun DrawScope.draw(animationFrame: ImageBitmap)
}