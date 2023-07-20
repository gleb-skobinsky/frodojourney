package com.game.frodojourney.app.character

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.Coordinates
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.character.CharacterTurned


val weaponCorrectionDifference = Coordinates(42f, 21f)

@Stable
data class PixelMainCharacter(
    override val position: Coordinates = Coordinates.Zero,
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
    override val isFighting: Boolean = false,
    override val isMoving: Boolean = false,
    val stepX: Dp = 0.dp,
    val stepY: Dp = 0.dp,
    override val characterFrame: ImageBitmap = LukeRun.calm,
    override val weapon: Weapon = Weapon(
        position = position + weaponCorrectionDifference,
        image = Weapons.lightSaber
    )
) : GenericCharacter {

    fun copyWeaponAware(
        position: Coordinates = this.position,
        turned: CharacterTurned = this.turned,
        isFighting: Boolean = this.isFighting,
        isMoving: Boolean = this.isMoving,
        stepX: Dp = this.stepX,
        stepY: Dp = this.stepY,
        characterFrame: ImageBitmap = this.characterFrame,
        weapon: Weapon = this.weapon.copy(position = this.position + weaponCorrectionDifference)
    ): PixelMainCharacter {
        return this.copy(
            position = position,
            turned = turned,
            isFighting = isFighting,
            isMoving = isMoving,
            stepX = stepX,
            stepY = stepY,
            characterFrame = characterFrame,
            weapon = weapon
        )
    }

    override fun DrawScope.draw(animationFrame: ImageBitmap, viewData: ViewData) {
        with(viewData) {
            val offset = position.toOffset()
            val center = offset.x + (animationFrame.width / 2f)
            scale(scaleX = turned.mirrorX, scaleY = 1f, pivot = Offset(center, offset.y)) {
                drawImage(
                    image = animationFrame,
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

    private fun ViewData.calculateWeaponOffset(): Offset {
        val weaponOffset = weapon.position.toOffset()
        return weaponOffset.copy(
            y = weaponOffset.y + weapon.image.height - 20f,
            x = weaponOffset.x + 10f
        )
    }
}

