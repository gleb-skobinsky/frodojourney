package com.game.frodojourney.app.canvas

import android.content.res.Configuration
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density

data class ViewData(
    val density: Density = Density(1f),
    val focus: Coordinates = Coordinates(750f, 1550f),
    val size: Size = Size.Zero,
    val orientation: Int = Configuration.ORIENTATION_PORTRAIT
) {
    fun Coordinates.toOffset() = Offset(
        x = (x - focus.x) + size.width / 2,
        y = (y - focus.y) + size.height / 2
    )

    fun Offset.toCoordinates() = Coordinates(
        (x - size.width / 2) / 1f + focus.x,
        -(y - size.height / 2) / 1f + focus.y
    )
}

fun calculateInitialFocus(
    size: Size
): Coordinates {
    return Coordinates(
        x = size.width / 2,
        y = size.height / 2
    )
}