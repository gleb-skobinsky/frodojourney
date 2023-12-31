package com.game.darkforce.app.character.enemies

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import com.game.darkforce.app.canvas.DpCoordinates
import com.game.darkforce.app.canvas.Drawing
import com.game.darkforce.app.canvas.ViewData
import com.game.darkforce.app.character.WeaponsResources

const val bulletSpeed = 3f

data class Bullet(
    val position: DpCoordinates,
    val rotation: Float
) : Drawing {
    override fun DrawScope.draw(viewData: ViewData) {
        with(viewData) {
            val offset = position.toOffset()
            rotate(rotation, pivot = offset) {
                drawImage(
                    image = WeaponsResources.bullet,
                    topLeft = offset,
                )
            }
        }
    }
}