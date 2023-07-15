package com.game.frodojourney.app.character

import android.content.res.Resources
import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.R
import com.game.frodojourney.app.imageResourceWithSize

object LukeRun {
    lateinit var calm: ImageBitmap
    lateinit var frame0: ImageBitmap
    lateinit var frame1: ImageBitmap
    lateinit var frame2: ImageBitmap
    lateinit var frame3: ImageBitmap
    lateinit var frame4: ImageBitmap
    lateinit var frame5: ImageBitmap
    lateinit var frame6: ImageBitmap
    lateinit var frame7: ImageBitmap
    lateinit var frame8: ImageBitmap
    lateinit var current: ImageBitmap
    fun load(resources: Resources) {
        frame0 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_0, resources)
        frame1 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_1, resources)
        frame2 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_2, resources)
        frame3 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_3, resources)
        frame4 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_4, resources)
        frame5 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_5, resources)
        frame6 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_6, resources)
        frame7 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_7, resources)
        frame8 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_8, resources)
        calm = ImageBitmap.imageResourceWithSize(R.drawable.lc_calm, resources)
        current = calm
    }

    operator fun iterator() = this

    operator fun hasNext(): Boolean = true

    operator fun next(): ImageBitmap = when (current) {
        calm -> {
            current = frame0
            frame0
        }

        frame0 -> {
            current = frame1
            frame1
        }

        frame1 -> {
            current = frame2
            frame2
        }

        frame2 -> {
            current = frame3
            frame3
        }

        frame3 -> {
            current = frame4
            frame4
        }

        frame4 -> {
            current = frame5
            frame5
        }

        frame5 -> {
            current = frame6
            frame6
        }

        frame6 -> {
            current = frame7
            frame7
        }

        frame7 -> {
            current = frame8
            frame8
        }

        frame8 -> {
            current = frame0
            frame0
        }

        else -> frame0
    }

    fun reset(): ImageBitmap {
        current = calm
        return calm
    }
}
