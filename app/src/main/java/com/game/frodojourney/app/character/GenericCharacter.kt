package com.game.frodojourney.app.character


import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.game.frodojourney.character.CharacterTurned
import com.game.frodojourney.viewmodel.Coordinates
import com.game.frodojourney.viewmodel.ViewData

interface GenericCharacter {
    val position: Coordinates
    val turned: CharacterTurned

    fun DrawScope.draw(animationFrame: ImageBitmap, viewData: ViewData)
}