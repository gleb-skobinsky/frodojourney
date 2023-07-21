package com.game.frodojourney.app.map

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.ViewData

val corusantEdges = listOf(
    Coordinates(0f, 790f),
    Coordinates(130f, 700f),
)

data class AllowedArea(
    val points: List<Coordinates>
) {
    fun DrawScope.draw(viewData: ViewData) {
        with(viewData) {
            val path = Path()
            points.forEachIndexed { index, coordinate ->
                val offset = coordinate.toOffset()
                if (index == 0) {
                    path.moveTo(offset.x, offset.y)
                } else {
                    path.lineTo(offset.x, offset.y)
                }
            }
            drawPath(
                path = path,
                color = Color.Red,
                style = Stroke(2.dp.toPx())
            )
        }
    }

    operator fun contains(value: Coordinates): Boolean {
        var count = 0
        for (i in 1 until points.size) {
            val (x1, y1) = points[i - 1].let { it.x to it.y }
            val (x2, y2) = points[i].let { it.x to it.y }
            if ((value.y < y1 != value.y < y2)
                && value.x < (x1 + ((value.y - y1) / (y2 - y1)) * (x2 - x1))
            ) {
                count += 1
            }
        }
        return count % 2 == 1
    }
}