package com.game.darkforce.app.character

import androidx.compose.ui.graphics.ImageBitmap
import com.game.darkforce.app.res.Loadable

abstract class Animation : Loadable {
    var current: Int = 0
    override val images: MutableList<ImageBitmap> = mutableListOf()
    fun next(): ImageBitmap {
        if (current == images.size - 1) {
            current = 0
        } else {
            current++
        }
        return images[current]
    }

    fun reset(): ImageBitmap {
        current = 0
        return images[current]
    }
}