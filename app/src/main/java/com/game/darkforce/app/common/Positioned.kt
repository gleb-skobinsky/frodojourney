package com.game.darkforce.app.common

import androidx.compose.ui.graphics.ImageBitmap
import com.game.darkforce.app.canvas.DpCoordinates

interface Positioned {
    val position: DpCoordinates
    val image: ImageBitmap
}