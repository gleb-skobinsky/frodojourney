package com.game.frodojourney.app

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowLeft
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.character.LukeRun
import com.game.frodojourney.character.CharacterTurned
import com.game.frodojourney.viewmodel.MainViewModel
import kotlinx.coroutines.delay

const val characterStep = 50

@Composable
fun BoxScope.ControllerArrowButton(
    arrow: ControllerArrow,
    viewModel: MainViewModel,
    onAnimate: (ImageBitmap) -> Unit
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
                    onTap = {
                        arrow.move(viewModel)
                    }
                )
            }
    )
    LaunchedEffect(longPressActive) {
        while (longPressActive) {
            arrow.move(viewModel)
            delay(100L)
        }
    }
    LaunchedEffect(longPressActive) {
        while (longPressActive) {
            onAnimate(LukeRun.next())
            delay(100L)
        }
        onAnimate(LukeRun.reset())
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
        when (this) {
            LEFT -> {
                viewModel.updateCharacterPosX((-characterStep).dp)
                viewModel.turnCharacter(CharacterTurned.LEFT)
            }

            RIGHT -> {
                viewModel.updateCharacterPosX(characterStep.dp)
                viewModel.turnCharacter(CharacterTurned.RIGHT)
            }

            UP -> {
                viewModel.updateCharacterPosY((-characterStep).dp)
            }

            DOWN -> {
                viewModel.updateCharacterPosY(characterStep.dp)
            }
        }
    }
}
