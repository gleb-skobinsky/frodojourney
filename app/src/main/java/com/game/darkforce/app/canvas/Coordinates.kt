package com.game.darkforce.app.canvas

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

typealias Coordinate = Float

data class Coordinates(
    val x: Coordinate,
    val y: Coordinate
) {

    operator fun minus(value: Size): Coordinates {
        return Coordinates(x = x - value.width, y = y - value.height)
    }

    operator fun plus(value: Size): Coordinates {
        return Coordinates(x = x + value.width, y = y + value.height)
    }

    operator fun plus(value: Coordinates): Coordinates {
        return Coordinates(x + value.x, y + value.y)
    }

    companion object {
        val Zero = Coordinates(0f, 0f)
    }
}

data class DpCoordinates(
    val x: Dp,
    val y: Dp
) {

    operator fun plus(value: DpCoordinates): DpCoordinates {
        return DpCoordinates(x + value.x, y + value.y)
    }

    companion object {
        val Zero = DpCoordinates(0.dp, 0.dp)
    }
}