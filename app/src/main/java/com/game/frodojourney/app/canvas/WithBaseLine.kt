package com.game.frodojourney.app.canvas

import androidx.compose.ui.graphics.ImageBitmap

interface WithBaseLine {
    val position: Coordinates
    val image: ImageBitmap
    val baseLine: Coordinate
}