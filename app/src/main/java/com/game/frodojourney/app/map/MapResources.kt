package com.game.frodojourney.app.map

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Density
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.imageResourceWithSize
import com.game.frodojourney.app.res.Loadable

object MapResources : Loadable {
    lateinit var corusant: ImageBitmap
    lateinit var corusantArch: ImageBitmap
    lateinit var corusantArch2: ImageBitmap
    lateinit var corusantLamp: ImageBitmap

    override fun load(resources: Resources, density: Density) {
        corusant = ImageBitmap.imageResourceWithSize(R.drawable.corusant, resources)
        corusantArch = ImageBitmap.imageResourceWithSize(R.drawable.corusant_arch, resources)
        corusantArch2 = ImageBitmap.imageResourceWithSize(R.drawable.corusant_arch2, resources)
        corusantLamp = ImageBitmap.imageResourceWithSize(R.drawable.corusant_lamp, resources)
    }
}