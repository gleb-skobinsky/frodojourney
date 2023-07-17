package com.game.frodojourney.app.map

import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.Coordinates


data class Corusant(
    override val characterInitialPosition: Coordinates = Coordinates(100f, 2600f),
    override val mapPosition: Coordinates = Coordinates.Zero,
    override val mapImage: ImageBitmap = MapResources.corusant,
    override val imageResource: Int = R.drawable.corusant
) : GameMap