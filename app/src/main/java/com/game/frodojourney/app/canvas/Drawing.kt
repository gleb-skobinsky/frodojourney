package com.game.frodojourney.app.canvas

import androidx.compose.ui.graphics.drawscope.DrawScope

interface Drawing {
    fun DrawScope.draw(viewData: ViewData)
}