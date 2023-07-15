package com.game.frodojourney.app.map

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.DpOffset

interface GenericMap {
    val startingPosition: DpOffset
    val characterInitialPosition: DpOffset
    val mapImage: ImageBitmap
}