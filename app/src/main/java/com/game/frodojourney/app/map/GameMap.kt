package com.game.frodojourney.app.map

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.app.canvas.Coordinates

interface GameMap {
    val mapPosition: Coordinates
    val characterInitialPosition: Coordinates
    val mapImage: ImageBitmap
    val imageResource: Int
    val imageSize: Size
}