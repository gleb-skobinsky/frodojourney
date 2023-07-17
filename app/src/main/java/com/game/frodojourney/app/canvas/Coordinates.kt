package com.game.frodojourney.app.canvas

import androidx.compose.ui.geometry.Size

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

    companion object {
        val Zero = Coordinates(0f, 0f)
    }
}