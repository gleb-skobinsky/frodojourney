package com.game.darkforce.app.composables

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun BoxScope.HpText(hp: Int) {
    Text(
        text = hp.toString(),
        modifier = Modifier.align(Alignment.TopStart),
        color = Color.White,
        fontSize = 20.sp
    )
}