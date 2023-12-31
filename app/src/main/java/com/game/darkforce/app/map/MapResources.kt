package com.game.darkforce.app.map

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import com.game.darkforce.R
import com.game.darkforce.app.canvas.imageResourceWithSize

object MapResources {
    lateinit var corusant: ImageBitmap
    lateinit var corusantArch: ImageBitmap
    lateinit var corusantArch2: ImageBitmap
    lateinit var corusantLamp: ImageBitmap

    fun load(resources: Resources) {
        corusant = ImageBitmap.imageResourceWithSize(R.drawable.corusant, resources)
        corusantArch = ImageBitmap.imageResourceWithSize(R.drawable.corusant_arch, resources)
        corusantArch2 = ImageBitmap.imageResourceWithSize(R.drawable.corusant_arch2, resources)
        corusantLamp = ImageBitmap.imageResourceWithSize(R.drawable.corusant_lamp, resources)
    }
}