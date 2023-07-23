package com.game.frodojourney.app.character.mainCharacter


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.Density
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.app.character.CharacterTurned
import com.game.frodojourney.app.character.Weapon
import com.game.frodojourney.app.common.Positioned

interface GenericCharacter : Positioned {
    override val position: DpCoordinates
    override val image: ImageBitmap
    val turned: CharacterTurned
    val isFighting: Boolean
    val isMoving: Boolean
    val weapon: Weapon

    fun characterBaseLine(density: Density) =
        with(density) { position.y.toPx() + image.height }

    private fun ViewData.calculateWeaponOffset(): Offset {
        val weaponOffset = weapon.position.toOffset()
        return weaponOffset.copy(
            y = weaponOffset.y + weapon.image.height - 20f,
            x = weaponOffset.x + 10f
        )
    }

    fun DrawScope.draw(viewData: ViewData) {
        with(viewData) {
            val offset = position.toOffset()
            val center = offset.x + (image.width / 2f)
            scale(scaleX = turned.mirrorX, scaleY = 1f, pivot = Offset(center, offset.y)) {
                drawImage(
                    image = image,
                    topLeft = offset
                )

                if (isFighting) {
                    rotate(
                        weapon.rotation,
                        pivot = calculateWeaponOffset()
                    ) {
                        drawImage(
                            image = weapon.image,
                            topLeft = weapon.position.toOffset()
                        )
                    }
                }
            }
        }
    }
}