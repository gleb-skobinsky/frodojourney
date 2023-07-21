package com.game.frodojourney.app.character


import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.character.CharacterTurned

data class Weapon(
    val position: Coordinates,
    val image: ImageBitmap,
    val rotation: Float = 0f
)

interface GenericCharacter {
    val position: Coordinates
    val turned: CharacterTurned
    val isFighting: Boolean
    val isMoving: Boolean
    val characterFrame: ImageBitmap
    val weapon: Weapon?

    fun characterBaseLine() = position.y + characterFrame.height

    fun DrawScope.draw(animationFrame: ImageBitmap, viewData: ViewData)
}