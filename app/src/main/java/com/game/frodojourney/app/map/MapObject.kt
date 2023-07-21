package com.game.frodojourney.app.map

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.DpCoordinates

data class MapObject(
    val position: DpCoordinates,
    val image: ImageBitmap
)

val corusantFrontObjects = listOf(
    MapObject(DpCoordinates(0.dp, 587.dp), MapResources.corusantArch)
)