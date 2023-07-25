package com.game.darkforce.app.character

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import com.game.darkforce.R
import com.game.darkforce.app.canvas.imageResourceWithSize

object WeaponsResources {
    lateinit var lightSaber: ImageBitmap
    lateinit var largeRifle: ImageBitmap
    lateinit var bullet: ImageBitmap

    fun load(resources: Resources) {
        lightSaber = ImageBitmap.imageResourceWithSize(R.drawable.lightsaber_only, resources)
        largeRifle = ImageBitmap.imageResourceWithSize(R.drawable.clone_blaster, resources)
        bullet = ImageBitmap.imageResourceWithSize(R.drawable.bullet, resources)
    }
}