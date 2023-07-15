package com.game.frodojourney.app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.game.frodojourney.R
import com.game.frodojourney.app.character.LukeRun
import com.game.frodojourney.viewmodel.MainViewModel

@Composable
fun App(viewModel: MainViewModel) {
    LukeRun.Init()
    val character by viewModel.character.collectAsState()
    val configuration = LocalConfiguration.current
    viewModel.setPlayableField(
        DpSize(
            configuration.screenWidthDp.dp,
            configuration.screenHeightDp.dp
        )
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.corusant),
            contentDescription = "Game map",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
        var characterFrame by remember {
            mutableStateOf(LukeRun.calm)
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            with(character) {
                draw(characterFrame)
            }
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .drawBehind {
                    drawCircle(
                        color = Color.LightGray,
                        radius = 67.dp.toPx(),
                        style = Stroke(30.dp.toPx())
                    )
                }
        ) {
            enumValues<ControllerArrow>().forEach { arrow ->
                ControllerArrowButton(
                    arrow = arrow,
                    viewModel = viewModel,
                    onAnimate = {
                        characterFrame = it
                    }
                )
            }
        }
    }
}
