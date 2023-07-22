package com.game.frodojourney.app.character.enemies

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.imageResourceWithSize
import com.game.frodojourney.app.res.Loadable

object EnemyResources : Loadable {
    lateinit var trooperImage: ImageBitmap

    override fun load(resources: Resources, density: Density) {
        trooperImage =
            with(density) {
                ImageBitmap.imageResourceWithSize(
                    R.drawable.clone_knee,
                    resources,
                    height = 40.dp.toPx().toInt(),
                    width = 38.dp.toPx().toInt()
                )
            }
    }
}