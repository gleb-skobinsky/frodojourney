package com.game.frodojourney.app.character.mainCharacter

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.character.CharacterTurned
import com.game.frodojourney.app.character.Weapon
import com.game.frodojourney.app.character.WeaponsResources


val weaponCorrectionDifference = DpCoordinates(12.dp, 6.dp)

@Stable
data class PixelMainCharacter(
    override val position: DpCoordinates = DpCoordinates(180.dp, 680.dp),
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
    override val isFighting: Boolean = false,
    override val isMoving: Boolean = false,
    val stepX: Dp = 0.dp,
    val stepY: Dp = 0.dp,
    override val image: ImageBitmap = LukeRun.calm,
    override val weapon: Weapon = Weapon(
        position = position + weaponCorrectionDifference,
        image = WeaponsResources.lightSaber
    )
) : GenericCharacter {

    fun copyWeaponAware(
        position: DpCoordinates = this.position,
        turned: CharacterTurned = this.turned,
        isFighting: Boolean = this.isFighting,
        isMoving: Boolean = this.isMoving,
        stepX: Dp = this.stepX,
        stepY: Dp = this.stepY,
        characterFrame: ImageBitmap = this.image,
        weapon: Weapon = this.weapon.copy(position = this.position + weaponCorrectionDifference)
    ): PixelMainCharacter {
        return this.copy(
            position = position,
            turned = turned,
            isFighting = isFighting,
            isMoving = isMoving,
            stepX = stepX,
            stepY = stepY,
            image = characterFrame,
            weapon = weapon
        )
    }
}
