package com.game.frodojourney.app.character

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.character.CharacterTurned


data class PixelMainCharacter(
    override val position: Coordinates = Coordinates.Zero,
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
    override val fighting: Boolean = false,
    val isMoving: Boolean = false,
    val stepX: Dp = 0.dp,
    val stepY: Dp = 0.dp
) : GenericCharacter {
    override fun DrawScope.draw(animationFrame: ImageBitmap, viewData: ViewData) {
        with(viewData) {
            val offset = position.toOffset()
            val center = offset.x + (animationFrame.width / 2f)
            scale(scaleX = turned.mirrorX, scaleY = 1f, pivot = Offset(center, offset.y)) {
                drawImage(
                    image = animationFrame,
                    topLeft = offset
                )
            }
        }
    }
}

