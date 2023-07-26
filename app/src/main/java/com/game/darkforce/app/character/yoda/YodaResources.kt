package com.game.darkforce.app.character.yoda

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import com.game.darkforce.R
import com.game.darkforce.app.canvas.imageResourceWithSize

object YodaResources {
    lateinit var skin: ImageBitmap
    fun load(resources: Resources) {
        skin = ImageBitmap.imageResourceWithSize(R.drawable.yoda_transp_s, resources)
    }
}