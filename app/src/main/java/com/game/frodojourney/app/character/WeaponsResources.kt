package com.game.frodojourney.app.character

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Density
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.imageResourceWithSize
import com.game.frodojourney.app.res.Loadable

object WeaponsResources : Loadable {
    lateinit var lightSaber: ImageBitmap
    lateinit var largeRifle: ImageBitmap

    override fun load(resources: Resources, density: Density) {
        lightSaber = ImageBitmap.imageResourceWithSize(R.drawable.lightsaber_only, resources)
        largeRifle = ImageBitmap.imageResourceWithSize(R.drawable.clone_blaster, resources)
    }
}