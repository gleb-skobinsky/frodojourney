package com.game.frodojourney.app.character

import androidx.compose.ui.graphics.ImageBitmap

interface Animation {

    fun next(): ImageBitmap

    fun reset(): ImageBitmap
}