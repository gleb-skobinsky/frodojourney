package com.game.frodojourney.app.canvas

import android.content.res.Configuration
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import com.game.frodojourney.app.character.GenericCharacter
import com.game.frodojourney.app.map.MapObject

@Stable
data class ViewData(
    val density: Density = Density(1f),
    val focus: Coordinates = Coordinates(750f, 1550f),
    val size: Size = Size.Zero,
    val orientation: Int = Configuration.ORIENTATION_UNDEFINED
) {
    fun Coordinates.toOffset() = Offset(
        x = (x - focus.x) + size.width / 2,
        y = (y - focus.y) + size.height / 2
    )

    fun Offset.toCoordinates() = Coordinates(
        (x - size.width / 2) / 1f + focus.x,
        -(y - size.height / 2) / 1f + focus.y
    )

    fun DpCoordinates.toOffset() = with(density) {
        Offset(
            x = (x.toPx() - focus.x) + size.width / 2,
            y = (y.toPx() - focus.y) + size.height / 2
        )
    }

    infix fun MapObject.isLowerThan(character: GenericCharacter): Boolean =
        character.characterBaseLine(density) < baseLine(density)

}

