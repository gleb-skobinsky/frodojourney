package com.game.frodojourney.app.character

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import com.game.frodojourney.app.toOffset
import com.game.frodojourney.character.CharacterTurned


data class PixelMainCharacter(
    override val position: DpOffset,
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
) : GenericCharacter {
    override fun DrawScope.draw(animationFrame: ImageBitmap) {
        val density = Density(density)
        val offset = position.toOffset(density)
        val center = offset.x + (animationFrame.width / 2f)
        scale(scaleX = turned.mirrorX, scaleY = 1f, pivot = Offset(center, offset.y)) {
            drawImage(
                image = animationFrame,
                topLeft = offset
            )
        }
    }
}

