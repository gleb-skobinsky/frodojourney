package com.game.darkforce.app.map

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.game.darkforce.app.canvas.DpCoordinates

data class MapObject(
    val position: DpCoordinates,
    val image: ImageBitmap
) {
    fun baseLine(density: Density) = with(density) { position.y.toPx() + image.height }
}

val corusantFrontObjects = listOf(
    MapObject(DpCoordinates(0.dp, 587.dp), MapResources.corusantArch),
    MapObject(DpCoordinates(1025.dp, 0.dp), MapResources.corusantArch2)
)

val corusantObjects = listOf(
    MapObject(DpCoordinates(472.dp, 171.dp), MapResources.corusantLamp)
)