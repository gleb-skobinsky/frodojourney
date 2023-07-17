package com.game.frodojourney.app.map

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.imageResourceWithSize

object MapResources {
    lateinit var corusant: ImageBitmap

    fun load(resources: Resources) {
        corusant = ImageBitmap.imageResourceWithSize(R.drawable.corusant, resources)
    }
}