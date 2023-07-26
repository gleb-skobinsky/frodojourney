package com.game.darkforce.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.game.darkforce.R
import com.game.darkforce.app.canvas.toOffset
import com.game.darkforce.app.composables.GamePlayingField
import com.game.darkforce.app.composables.MainGamingController
import com.game.darkforce.viewmodel.MainViewModel

@Composable
fun App(viewModel: MainViewModel) {
    val hp by viewModel.hp.collectAsState()
    val overlayOpen by viewModel.openRiddle.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .gesturesDisabled(overlayOpen)
    ) {
        GamePlayingField(
            viewModel = viewModel
        )
        LightSaberController(viewModel)
        MainGamingController(
            viewModel = viewModel
        )
        HpText(hp)
        Overlay(overlayOpen)
    }
}

@Composable
private fun Overlay(overlayOpen: Boolean) {
    AnimatedVisibility(visible = overlayOpen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f))
        ) {
            Text(
                text = "Неплохо, мой падаван",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // we should wait for all new pointer events
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        this
    }


@Composable
private fun BoxScope.HpText(hp: Int) {
    Text(
        text = hp.toString(),
        modifier = Modifier.Companion.align(Alignment.TopStart),
        color = Color.White,
        fontSize = 20.sp
    )
}

@Composable
fun BoxScope.LightSaberController(viewModel: MainViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .align(Alignment.BottomStart)
            .size(72.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                viewModel.fightWithLightSaber()
            }
    ) {
        GlowingBorder()
        Image(
            painter = painterResource(id = R.drawable.lightsaber),
            contentDescription = "Use light saber"
        )
    }
}

val lightBlue = Color(0, 100, 255)

@Composable
private fun GlowingBorder() {
    Row(
        Modifier
            .fillMaxSize()
            .drawWithCache {
                with(Density(density)) {
                    val center = (size / 2f).toOffset()
                    val paint = Paint().apply {
                        style = PaintingStyle.Stroke
                        strokeWidth = 60f
                    }

                    val frameworkPaint = paint.asFrameworkPaint()

                    val color = lightBlue
                    val transparent = color
                        .copy(alpha = 0f)
                        .toArgb()
                    frameworkPaint.color = transparent
                    frameworkPaint.setShadowLayer(
                        20f,
                        0f,
                        0f,
                        color
                            .copy(alpha = .5f)
                            .toArgb()
                    )
                    val radius = 32.dp.toPx()
                    onDrawBehind {
                        drawIntoCanvas {
                            it.drawCircle(
                                center = center,
                                radius = radius,
                                paint = paint
                            )
                            drawCircle(
                                color = Color.White,
                                radius = radius,
                                center = center,
                                style = Stroke(width = 4.dp.toPx())
                            )
                        }
                    }
                }
            }
    ) {

    }
}
