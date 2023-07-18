package com.game.frodojourney.app.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.game.frodojourney.app.character.PixelMainCharacter
import kotlinx.coroutines.delay

@Composable
fun HandleMovementAndAnimation(
    character: PixelMainCharacter,
    onMove: () -> Unit,
    onAnimate: () -> Unit,
    onComplete: () -> Unit
) {
    LaunchedEffect(key1 = character.isMoving) {
        while (character.isMoving) {
            onMove()
            delay(50L)
        }
    }
    LaunchedEffect(key1 = character.isMoving) {
        while (character.isMoving) {
            onAnimate()
            delay(100L)
        }
        onComplete()
    }
}
