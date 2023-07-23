package com.game.frodojourney.app.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Density
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.app.character.enemies.Squad
import com.game.frodojourney.app.character.mainCharacter.PixelMainCharacter
import com.game.frodojourney.viewmodel.MainViewModel
import com.game.frodojourney.viewmodel.MapState

@Composable
fun GamePlayingField(
    viewData: ViewData,
    viewModel: MainViewModel,
    mapState: MapState,
    character: PixelMainCharacter,
    squad: Squad
) {
    val configuration = LocalConfiguration.current
    val objectsToDraw = remember(mapState.map.objects, character) {
        with(viewData) {
            mapState.map.objects.filter { it isLowerThan character }
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (viewData.size == Size.Zero) {
            viewModel.setViewData(
                ViewData(
                    density = Density(density),
                    size = size
                )
            )
            viewModel.updateOrientation(configuration.orientation)
        } else if (viewData.size != size) {
            viewModel.setViewData(viewData.copy(size = size))
            viewModel.updateOrientation(
                configuration.orientation
            )
        }
        with(mapState.map) {
            draw(viewData)
        }

        with(character) {
            draw(viewData)
        }

        for (trooper in squad) {
            with(trooper) {
                draw(viewData)
            }
        }

        with(mapState.map) {
            drawFrontObjects(viewData)
            drawObjects(objectsToDraw, viewData)
        }

        // with(mapState.map.allowedArea) { draw(viewData) }
    }
}

