package com.game.frodojourney.app.character

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.imageResourceWithSize

object WeaponsResources {
    lateinit var lightSaber: ImageBitmap
    lateinit var largeRifle: ImageBitmap
    lateinit var bullet: ImageBitmap

    fun load(resources: Resources) {
        lightSaber = ImageBitmap.imageResourceWithSize(R.drawable.lightsaber_only, resources)
        largeRifle = ImageBitmap.imageResourceWithSize(R.drawable.clone_blaster, resources)
        bullet = ImageBitmap.imageResourceWithSize(R.drawable.blaster, resources)
    }
}