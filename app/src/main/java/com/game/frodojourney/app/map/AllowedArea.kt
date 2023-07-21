package com.game.frodojourney.app.map

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.canvas.ViewData

val corusantEdges = listOf(
    DpCoordinates(200.dp, 833.dp),
    DpCoordinates(30.dp, 833.dp),
    DpCoordinates(30.dp, 770.dp),
    DpCoordinates(130.dp, 700.dp),
    DpCoordinates(220.dp, 550.dp),
    DpCoordinates(180.dp, 510.dp),
    DpCoordinates(180.dp, 420.dp),
    DpCoordinates(400.dp, 560.dp),
    DpCoordinates(300.dp, 660.dp),
    DpCoordinates(300.dp, 660.dp),
    DpCoordinates(250.dp, 740.dp),
    DpCoordinates(270.dp, 760.dp),
    DpCoordinates(200.dp, 833.dp),
)

data class AllowedArea(
    val points: List<DpCoordinates>
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

    operator fun contains(value: DpCoordinates): Boolean {
        val valueYAsFloat = value.y.value
        val valueXAsFloat = value.x.value
        var count = 0
        for (i in 1 until points.size) {
            val (x1, y1) = points[i - 1].let { it.x.value to it.y.value }
            val (x2, y2) = points[i].let { it.x.value to it.y.value }
            if ((valueYAsFloat < y1 != valueYAsFloat < y2)
                && valueXAsFloat < (x1 + ((valueYAsFloat - y1) / (y2 - y1)) * (x2 - x1))
            ) {
                count += 1
            }
        }
        return count % 2 == 1
    }
}