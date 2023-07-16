package com.game.frodojourney.app.character

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import com.game.frodojourney.character.CharacterTurned
import com.game.frodojourney.viewmodel.Coordinates
import com.game.frodojourney.viewmodel.ViewData


data class PixelMainCharacter(
    override val position: Coordinates,
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
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

