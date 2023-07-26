package com.game.darkforce.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.game.darkforce.app.composables.GamePlayingField
import com.game.darkforce.app.composables.HpText
import com.game.darkforce.app.composables.LightSaberController
import com.game.darkforce.app.composables.MainGamingController
import com.game.darkforce.app.composables.Overlay
import com.game.darkforce.app.composables.gesturesDisabled
import com.game.darkforce.viewmodel.MainViewModel

@Composable
fun App(viewModel: MainViewModel) {
    val hp by viewModel.hp.collectAsState()
    val overlayOpen by viewModel.openRiddle.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .gesturesDisabled(overlayOpen)
    ) {
        GamePlayingField(
            viewModel = viewModel
        )
        LightSaberController(viewModel)
        MainGamingController(
            viewModel = viewModel
        )
        HpText(hp)
        Overlay(overlayOpen)
    }
}
