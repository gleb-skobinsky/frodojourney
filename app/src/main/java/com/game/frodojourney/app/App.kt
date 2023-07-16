package com.game.frodojourney.app

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.character.LukeRun
import com.game.frodojourney.character.CharacterTurned
import com.game.frodojourney.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.pow

const val controllerRadius = 67f
const val innerRadius = 40f
const val controllerCenterX = 100f
const val controllerCenterY = 100f
val centerDpOffset = DpOffset(controllerCenterX.dp, controllerCenterY.dp)

@Composable
fun App(viewModel: MainViewModel) {
    viewModel.localDensity = LocalDensity.current
    val character by viewModel.character.collectAsState()
    val characterFrame by viewModel.characterFrame.collectAsState()
    val mapState by viewModel.mapState.collectAsState()
    val viewData by viewModel.viewData.collectAsState()
    val configuration = LocalConfiguration.current
    viewModel.setPlayableField(
        DpSize(
            configuration.screenWidthDp.dp,
            configuration.screenHeightDp.dp
        ),
        configuration.orientation
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var joystickDrag by remember { mutableStateOf(DpOffset.Zero) }
        var characterIsMoving by remember { mutableStateOf(false) }
        var characterStepX by remember { mutableStateOf(0.dp) }
        var characterStepY by remember { mutableStateOf(0.dp) }
        var characterTurn by remember { mutableStateOf(CharacterTurned.RIGHT) }
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (viewData.size != size) {
                viewModel.resizeMap(size)
            }
            with(viewData) {
                drawImage(
                    image = mapState.map.mapImage,
                    topLeft = mapState.map.mapPosition.toOffset()
                )
            }

            with(character) {
                characterFrame?.let { draw(it, viewData) }
            }
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .drawBehind {
                    drawCircle(
                        color = Color.LightGray,
                        radius = controllerRadius.dp.toPx(),
                        center = Offset(controllerCenterX.dp.toPx(), controllerCenterY.dp.toPx()),
                        style = Stroke(30.dp.toPx())
                    )
                }
        ) {
            enumValues<ControllerArrow>().forEach { arrow ->
                ControllerArrowButton(
                    arrow = arrow,
                    viewModel = viewModel,
                    onAnimate = {
                        viewModel.setFrame(it)
                    }
                )
            }
            Row(
                Modifier
                    .offset(joystickDrag.x, joystickDrag.y)
                    .size(56.dp)
                    .align(Alignment.Center)
                    .shadow(20.dp, RoundedCornerShape(50))
                    .background(Color.LightGray, RoundedCornerShape(50))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                characterIsMoving = false
                                joystickDrag = DpOffset.Zero
                            },
                            onDragCancel = {
                                characterIsMoving = false
                                joystickDrag = DpOffset.Zero
                            }
                        ) { _, dragAmount ->
                            characterIsMoving = true
                            val dragAmountDp = DpOffset(dragAmount.x.toDp(), dragAmount.y.toDp())
                            val againstCenter = joystickDrag + dragAmountDp
                            val newOffset = centerDpOffset + againstCenter
                            if (newOffset.isInBounds()) joystickDrag += dragAmountDp
                            val absoluteDrag = DpOffset(
                                abs(againstCenter.x.value).dp,
                                abs(againstCenter.y.value).dp
                            )
                            when {
                                absoluteDrag.x > absoluteDrag.y -> {
                                    val ratio = absoluteDrag.y / absoluteDrag.x
                                    val xDirectionIsPositive = againstCenter.x > 0.dp
                                    characterTurn =
                                        if (xDirectionIsPositive) CharacterTurned.RIGHT else CharacterTurned.LEFT
                                    characterStepX =
                                        if (xDirectionIsPositive) characterStep.dp else (-characterStep).dp
                                    characterStepY =
                                        if (dragAmount.y > 0) (characterStep * ratio).dp else (-characterStep * ratio).dp
                                }

                                absoluteDrag.y > absoluteDrag.x -> {
                                    val ratio = absoluteDrag.x / absoluteDrag.y
                                    val xDirectionIsPositive = againstCenter.x > 0.dp
                                    characterTurn =
                                        if (xDirectionIsPositive) CharacterTurned.RIGHT else CharacterTurned.LEFT
                                    characterStepX =
                                        if (xDirectionIsPositive) (characterStep * ratio).dp else (-characterStep * ratio).dp
                                    characterStepY =
                                        if (dragAmount.y > 0) characterStep.dp else (-characterStep).dp
                                }
                            }
                        }
                    }
            ) {}
        }

        LaunchedEffect(characterIsMoving) {
            while (characterIsMoving) {
                viewModel.turnCharacter(characterTurn)
                viewModel.updateCharacterPosX(characterStepX)
                viewModel.updateCharacterPosY(characterStepY)
                delay(50L)
            }
        }
        LaunchedEffect(characterIsMoving) {
            while (characterIsMoving) {
                viewModel.setFrame(LukeRun.next())
                delay(100L)
            }
            viewModel.setFrame(LukeRun.reset())
        }
    }
}

private fun DpOffset.isInBounds(): Boolean =
    (x.value - controllerCenterX).pow(2) + (y.value - controllerCenterY).pow(2) < innerRadius.pow(2)

