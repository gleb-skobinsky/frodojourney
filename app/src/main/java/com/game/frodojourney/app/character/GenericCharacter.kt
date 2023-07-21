package com.game.frodojourney.app.character


import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.character.CharacterTurned

data class Weapon(
    val position: DpCoordinates,
    val image: ImageBitmap,
    val rotation: Float = 0f
)

interface GenericCharacter {
    val position: DpCoordinates
    val turned: CharacterTurned
    val isFighting: Boolean
    val isMoving: Boolean
    val characterFrame: ImageBitmap
    val weapon: Weapon?

    fun characterBaseLine(density: Density) =
        with(density) { position.y.toPx() + characterFrame.height }

    fun DrawScope.draw(animationFrame: ImageBitmap, viewData: ViewData)
}