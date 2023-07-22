package com.game.frodojourney.app.canvas

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset

fun DpOffset.toOffset(density: Density) = with(density) { Offset(x.toPx(), y.toPx()) }

fun Offset.toDpOffset(density: Density) = with(density) { DpOffset(x.toDp(), y.toDp()) }

fun Size.toOffset() = Offset(width, height)


fun ImageBitmap.Companion.imageResourceWithSize(
    @DrawableRes id: Int,
    res: Resources,
    width: Int? = null,
    height: Int? = null
): ImageBitmap {
    val bitmap = BitmapFactory.decodeResource(res, id)
    var actualWidth = width
    var actualHeight = height
    val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()
    when {
        width == null && height == null -> {
            actualWidth = bitmap.width
            actualHeight = bitmap.height
        }

        width == null && height != null -> {
            actualWidth = (actualHeight!! * ratio).toInt()
        }

        width != null && height == null -> {
            actualHeight = (actualWidth!! / ratio).toInt()
        }
    }
    val scaledBitmap =
        Bitmap.createScaledBitmap(bitmap, actualWidth!!, actualHeight!!, true)
    return scaledBitmap.asImageBitmap()
}

/*
fun ImageBitmap.toTurn(turned: CharacterTurned): ImageBitmap {
    val m = Matrix()
    m.preScale(turned.mirrorX, 1f)
    val src: Bitmap = this.asAndroidBitmap()
    return Bitmap.createBitmap(src, 0, 0, src.width, src.height, m, false).asImageBitmap()
}
 */
