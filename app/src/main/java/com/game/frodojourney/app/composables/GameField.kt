package com.game.frodojourney.app.composables

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Density
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.app.canvas.calculateInitialFocus
import com.game.frodojourney.app.character.PixelMainCharacter
import com.game.frodojourney.viewmodel.MainViewModel
import com.game.frodojourney.viewmodel.MapState

@Composable
fun GamePlayingField(
    viewData: ViewData,
    viewModel: MainViewModel,
    configuration: Configuration,
    mapState: MapState,
    character: PixelMainCharacter,
    characterFrame: ImageBitmap?
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (viewData.size == Size.Zero) {
            val posX = size.width / 7f
            val posY = size.height - (size.height / 7f)
            viewModel.setInitialCharacterPosition(Coordinates(posX, posY))
            viewModel.updateOrientation(configuration.orientation)
            viewModel.setViewData(
                ViewData(
                    density = Density(density),
                    focus = calculateInitialFocus(size),
                    size = size
                )
            )
        } else if (viewData.size != size) {
            viewModel.setViewData(viewData.copy(size = size))
            viewModel.updateOrientation(
                configuration.orientation
            )
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
}