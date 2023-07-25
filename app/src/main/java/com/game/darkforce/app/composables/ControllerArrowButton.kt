package com.game.darkforce.app.composables

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowLeft
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

const val characterStep = 3

@Composable
fun BoxScope.ControllerArrowButton(
    arrow: ControllerArrow
) {
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
    )
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
}
