package com.game.frodojourney.app.common

import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.app.canvas.DpCoordinates

interface Positioned {
    val position: DpCoordinates
    val image: ImageBitmap
}