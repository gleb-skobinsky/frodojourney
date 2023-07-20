package com.game.frodojourney.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.game.frodojourney.R
import com.game.frodojourney.app.canvas.toOffset
import com.game.frodojourney.app.composables.GamePlayingField
import com.game.frodojourney.app.composables.MainGamingController
import com.game.frodojourney.viewmodel.MainViewModel

@Composable
fun App(viewModel: MainViewModel) {
    val character by viewModel.character.collectAsState()
    val mapState by viewModel.mapState.collectAsState()
    val viewData by viewModel.viewData.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GamePlayingField(
            viewData = viewData,
            viewModel = viewModel,
            mapState = mapState,
            character = character
        )
        LightSaberController(viewModel)
        MainGamingController(
            viewModel = viewModel
        )
    }
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
