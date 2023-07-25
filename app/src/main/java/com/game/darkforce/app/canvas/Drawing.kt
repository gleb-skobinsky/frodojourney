package com.game.darkforce.app.canvas

import androidx.compose.ui.graphics.drawscope.DrawScope

interface Drawing {
    fun DrawScope.draw(viewData: ViewData)
}