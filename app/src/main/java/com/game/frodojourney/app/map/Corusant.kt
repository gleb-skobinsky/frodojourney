package com.game.frodojourney.app.map

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp


data class Corusant(
    override val startingPosition: DpOffset = DpOffset(0.dp, (-30).dp),
    override val characterInitialPosition: DpOffset = DpOffset(30.dp, 700.dp),
    override val mapImage: ImageBitmap = MapResources.corusant
): GenericMap