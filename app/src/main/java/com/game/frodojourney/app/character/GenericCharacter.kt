package com.game.frodojourney.app.character


import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.character.CharacterTurned

interface GenericCharacter {
    val position: Coordinates
    val turned: CharacterTurned

    fun DrawScope.draw(animationFrame: ImageBitmap, viewData: ViewData)
}