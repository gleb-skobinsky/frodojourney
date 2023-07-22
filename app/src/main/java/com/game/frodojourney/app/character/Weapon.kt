package com.game.frodojourney.app.character

import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.common.Positioned

data class Weapon(
    override val position: DpCoordinates,
    override val image: ImageBitmap,
    val rotation: Float = 0f
): Positioned
