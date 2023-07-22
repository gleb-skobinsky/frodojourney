package com.game.frodojourney.app.character.enemies

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.imageResourceWithSize
import com.game.frodojourney.app.res.Loadable

object EnemyResources : Loadable {
    lateinit var trooperImage: ImageBitmap

    override fun load(resources: Resources) {
        trooperImage = ImageBitmap.imageResourceWithSize(R.drawable.clone_knee, resources)
    }
}