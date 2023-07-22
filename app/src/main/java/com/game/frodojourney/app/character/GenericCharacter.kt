package com.game.frodojourney.app.character


import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.app.common.Positioned

interface GenericCharacter : Positioned {
    override val position: DpCoordinates
    override val image: ImageBitmap
    val turned: CharacterTurned
    val isFighting: Boolean
    val isMoving: Boolean
    val weapon: Weapon?

    fun characterBaseLine(density: Density) =
        with(density) { position.y.toPx() + image.height }

    fun DrawScope.draw(animationFrame: ImageBitmap, viewData: ViewData)
}