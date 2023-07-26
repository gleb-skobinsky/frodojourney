package com.game.darkforce.app.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Overlay(overlayOpen: Boolean) {
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
