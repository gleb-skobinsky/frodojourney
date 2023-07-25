package com.game.darkforce.app.character

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.game.darkforce.app.canvas.DpCoordinates
import com.game.darkforce.app.common.Positioned

data class Weapon(
    override val position: DpCoordinates = DpCoordinates(0.dp, 0.dp),
    override val image: ImageBitmap = WeaponsResources.largeRifle,
    val rotation: Float = 0f
) : Positioned
