package com.game.frodojourney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowLeft
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.game.frodojourney.ui.theme.FrodoJourneyTheme
import com.game.frodojourney.viewmodel.MainViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val gameViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrodoJourneyTheme {
                // A surface container using the 'background' color from the theme
                App(gameViewModel)
            }
        }
    }
}

@Composable
fun App(viewModel: MainViewModel) {
    val character by viewModel.character.collectAsState()
    val characterBitmap = ImageBitmap.imageResource(R.drawable.user)
    val density = LocalDensity.current
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
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawImage(
                image = characterBitmap,
                topLeft = character.position.toOffset(density)
            )
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
            enumValues<ControllerArrow>().forEach {
                ControllerArrowButton(it, viewModel)
            }
        }
    }
}

@Composable
fun BoxScope.ControllerArrowButton(
    arrow: ControllerArrow,
    viewModel: MainViewModel
) {
    var longPressActive by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Icon(
        imageVector = arrow.vector,
        contentDescription = "Move left",
        tint = Color.Gray,
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(50))
            .rotate(arrow.degrees)
            .align(arrow.alignment)
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { longPressActive = true },
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        awaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                        longPressActive = false
                    },
                    onTap = { arrow.move(viewModel) }
                )
            }
        // .clickable { arrow.move(viewModel, playableFieldSize) }
    )
    LaunchedEffect(key1 = longPressActive) {
        while (longPressActive) {
            arrow.move(viewModel)
            delay(300L)
        }
    }
}

enum class ControllerArrow(
    val vector: ImageVector,
    val degrees: Float,
    val alignment: Alignment
) {
    LEFT(Icons.Outlined.ArrowLeft, 0f, Alignment.CenterStart),
    RIGHT(Icons.Outlined.ArrowRight, 0f, Alignment.CenterEnd),
    UP(Icons.Outlined.ArrowLeft, 90f, Alignment.TopCenter),
    DOWN(Icons.Outlined.ArrowRight, 90f, Alignment.BottomCenter);

    fun move(viewModel: MainViewModel) {
        println("Player moved")
        when (this) {
            LEFT -> viewModel.updateCharacterPosX((-30).dp)
            RIGHT -> viewModel.updateCharacterPosX(30.dp)
            UP -> viewModel.updateCharacterPosY((-30).dp)
            DOWN -> viewModel.updateCharacterPosY(30.dp)
        }
    }
}

fun DpOffset.toOffset(density: Density) = with(density) { Offset(x.toPx(), y.toPx()) }


