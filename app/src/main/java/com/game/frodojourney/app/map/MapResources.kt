package com.game.frodojourney.app.map

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.imageResourceWithSize

object MapResources {
    lateinit var corusant: ImageBitmap
    lateinit var corusantArch: ImageBitmap
    lateinit var corusantLamp: ImageBitmap

    fun load(resources: Resources) {
        corusant = ImageBitmap.imageResourceWithSize(R.drawable.corusant, resources)
        corusantArch = ImageBitmap.imageResourceWithSize(R.drawable.corusant_arch, resources)
        corusantLamp = ImageBitmap.imageResourceWithSize(R.drawable.corusant_lamp, resources)
    }
}