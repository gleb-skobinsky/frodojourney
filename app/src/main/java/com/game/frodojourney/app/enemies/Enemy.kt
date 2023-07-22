package com.game.frodojourney.app.enemies

import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.common.Positioned

data class Enemy(
    override val position: DpCoordinates,
    override val image: ImageBitmap
) : Positioned