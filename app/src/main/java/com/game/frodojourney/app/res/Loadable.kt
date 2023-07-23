package com.game.frodojourney.app.res

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.app.canvas.imageResourceWithSize

interface Loadable {
    val ids: List<Int>
    val images: MutableList<ImageBitmap>
    fun load(resources: Resources) {
        images.clear()
        for (id in ids) {
            val image = ImageBitmap.imageResourceWithSize(id, resources)
            images += image
        }
    }
}