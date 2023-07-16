package com.game.frodojourney.app.map

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.DpOffset
import com.game.frodojourney.viewmodel.Coordinates

interface GameMap {
    val mapPosition: Coordinates
    val characterInitialPosition: Coordinates
    val mapImage: ImageBitmap
    val imageResource: Int
}