package com.game.frodojourney.app.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.ControllerArrow
import com.game.frodojourney.app.ControllerArrowButton
import com.game.frodojourney.app.characterStep
import com.game.frodojourney.character.CharacterTurned
import com.game.frodojourney.viewmodel.MainViewModel
import kotlin.math.abs
import kotlin.math.pow

const val controllerRadius = 67f
const val innerRadius = 40f
const val controllerCenterX = 100f
const val controllerCenterY = 100f
val centerDpOffset = DpOffset(controllerCenterX.dp, controllerCenterY.dp)

@Composable
fun BoxScope.MainGamingController(
    viewModel: MainViewModel,
    joystickDrag: MutableState<DpOffset>
) {
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
        Joystick(joystickDrag, viewModel)
    }
}

@Composable
private fun BoxScope.Joystick(
    joystickDrag: MutableState<DpOffset>,
    viewModel: MainViewModel
) {
    Row(
        Modifier
            .offset(joystickDrag.value.x, joystickDrag.value.y)
            .size(56.dp)
            .align(Alignment.Center)
            .shadow(20.dp, RoundedCornerShape(50))
            .background(Color.LightGray, RoundedCornerShape(50))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        viewModel.stopMovement()
                        joystickDrag.value = DpOffset.Zero
                    },
                    onDragCancel = {
                        viewModel.stopMovement()
                        joystickDrag.value = DpOffset.Zero
                    }
                ) { _, dragAmount ->
                    val dragAmountDp = DpOffset(dragAmount.x.toDp(), dragAmount.y.toDp())
                    val againstCenter = joystickDrag.value + dragAmountDp
                    val newOffset = centerDpOffset + againstCenter
                    if (newOffset.isInBounds()) joystickDrag.value += dragAmountDp
                    val absoluteDrag = DpOffset(
                        abs(againstCenter.x.value).dp,
                        abs(againstCenter.y.value).dp
                    )
                    when {
                        absoluteDrag.x > absoluteDrag.y -> {
                            val ratio = absoluteDrag.y / absoluteDrag.x
                            val xDirectionIsPositive = againstCenter.x > 0.dp
                            val characterTurn =
                                if (xDirectionIsPositive) CharacterTurned.RIGHT else CharacterTurned.LEFT
                            val characterStepX =
                                if (xDirectionIsPositive) characterStep.dp else (-characterStep).dp
                            val characterStepY =
                                if (dragAmount.y > 0) (characterStep * ratio).dp else (-characterStep * ratio).dp
                            viewModel.startMovement(
                                characterTurn,
                                characterStepX,
                                characterStepY
                            )
                        }

                        absoluteDrag.y > absoluteDrag.x -> {
                            val ratio = absoluteDrag.x / absoluteDrag.y
                            val xDirectionIsPositive = againstCenter.x > 0.dp
                            val characterTurn =
                                if (xDirectionIsPositive) CharacterTurned.RIGHT else CharacterTurned.LEFT
                            val characterStepX =
                                if (xDirectionIsPositive) (characterStep * ratio).dp else (-characterStep * ratio).dp
                            val characterStepY =
                                if (dragAmount.y > 0) characterStep.dp else (-characterStep).dp
                            viewModel.startMovement(
                                characterTurn,
                                characterStepX,
                                characterStepY
                            )
                        }
                    }
                }
            }
    ) {}
}

fun DpOffset.isInBounds(): Boolean =
    (x.value - controllerCenterX).pow(2) + (y.value - controllerCenterY).pow(2) < innerRadius.pow(2)
