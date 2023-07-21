package com.game.frodojourney.app.map

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.ViewData

@Immutable
data class Corusant(
    override val characterInitialPosition: Coordinates = Coordinates(100f, 2600f),
    override val mapPosition: Coordinates = Coordinates.Zero,
    override val mapImage: ImageBitmap = MapResources.corusant,
    override val imageResource: Int = R.drawable.corusant,
    override val imageSize: Size = Size(mapImage.width.toFloat(), mapImage.height.toFloat()),
    override val frontObjects: List<MapObject> = corusantFrontObjects,
    override val objects: List<MapObject> = corusantObjects
) : GameMap {
    override fun DrawScope.draw(viewData: ViewData) {
        with(viewData) {
            drawImage(
                image = mapImage,
                topLeft = mapPosition.toOffset()
            )
        }
    }

    override fun DrawScope.drawFrontObjects(viewData: ViewData) {
        with(viewData) {
            for (obj in frontObjects) {
                drawImage(
                    image = obj.image,
                    topLeft = obj.position.toOffset()
                )
            }
        }
    }

    override fun DrawScope.drawObjects(filteredObjects: List<MapObject>, viewData: ViewData) {
        with(viewData) {
            for (obj in filteredObjects) {
                drawImage(
                    image = obj.image,
                    topLeft = obj.position.toOffset()
                )
            }
        }
    }
}