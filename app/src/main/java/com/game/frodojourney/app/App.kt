package com.game.frodojourney.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.game.frodojourney.app.character.LukeRun
import com.game.frodojourney.app.composables.GamePlayingField
import com.game.frodojourney.app.composables.HandleMovementAndAnimation
import com.game.frodojourney.app.composables.MainGamingController
import com.game.frodojourney.viewmodel.MainViewModel

@Composable
fun App(viewModel: MainViewModel) {
    val character by viewModel.character.collectAsState()
    val characterFrame by viewModel.characterFrame.collectAsState()
    val mapState by viewModel.mapState.collectAsState()
    val viewData by viewModel.viewData.collectAsState()
    val configuration = LocalConfiguration.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GamePlayingField(
            viewData = viewData,
            viewModel = viewModel,
            configuration = configuration,
            mapState = mapState,
            character = character,
            characterFrame = characterFrame
        )
        MainGamingController(
            viewModel = viewModel
        )
        HandleMovementAndAnimation(
            character = character,
            onMove = {
                viewModel.turnCharacter(character.turned)
                viewModel.updateCharacterPosX(character.stepX)
                viewModel.updateCharacterPosY(character.stepY)
            },
            onAnimate = {
                viewModel.setFrame(LukeRun.next())
            },
            onComplete = {
                viewModel.setFrame(LukeRun.reset())
            }
        )
    }
}
