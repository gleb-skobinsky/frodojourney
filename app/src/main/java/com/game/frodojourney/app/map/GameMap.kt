package com.game.frodojourney.app.map

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.ViewData

interface GameMap {
    val mapPosition: Coordinates
    val characterInitialPosition: Coordinates
    val mapImage: ImageBitmap
    val imageResource: Int
    val imageSize: Size
    val frontObjects: List<MapObject>
    val objects: List<MapObject>

    fun DrawScope.draw(viewData: ViewData)

    fun DrawScope.drawFrontObjects(viewData: ViewData)

    fun DrawScope.drawObjects(filteredObjects: List<MapObject>, viewData: ViewData)
}