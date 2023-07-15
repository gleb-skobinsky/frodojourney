package com.game.frodojourney.app.character

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import com.game.frodojourney.R
import com.game.frodojourney.app.imageResourceWithSize
import com.game.frodojourney.app.toOffset
import com.game.frodojourney.character.CharacterTurned


data class PixelMainCharacter(
    override val position: DpOffset = DpOffset.Zero,
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
) : GenericCharacter {
    override fun DrawScope.draw(animationFrame: ImageBitmap) {
        val density = Density(density)
        val offset = position.toOffset(density)
        val center = offset.x + (animationFrame.width / 2f)
        scale(scaleX = turned.mirrorX, scaleY = 1f, pivot = Offset(center, offset.y)) {
            drawImage(
                image = animationFrame,
                topLeft = offset
            )
        }
    }
}

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

    @Composable
    fun Init() {
        frame0 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_0)
        frame1 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_1)
        frame2 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_2)
        frame3 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_3)
        frame4 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_4)
        frame5 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_5)
        frame6 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_6)
        frame7 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_7)
        frame8 = ImageBitmap.imageResourceWithSize(R.drawable.ls_run_8)
        calm = ImageBitmap.imageResourceWithSize(R.drawable.lc_calm)
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