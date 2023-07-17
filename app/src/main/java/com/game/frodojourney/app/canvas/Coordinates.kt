package com.game.frodojourney.app.canvas

typealias Coordinate = Float

data class Coordinates(
    val x: Coordinate,
    val y: Coordinate
) {
    companion object {
        val Zero = Coordinates(0f, 0f)
    }
}