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
data class MainHero(
    override val position: DpCoordinates = DpCoordinates(180.dp, 680.dp),
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
    override val isFighting: Boolean = false,
    override val isMoving: Boolean = false,
    val stepX: Dp = 0.dp,
    val stepY: Dp = 0.dp,
    override val image: ImageBitmap = LukeRun.reset(),
    override val weapon: Weapon = Weapon(
        position = position + weaponCorrectionDifference,
        image = WeaponsResources.lightSaber
    )
) : GenericCharacter {

    val center = DpCoordinates(position.x + 20.dp, position.y + 30.dp)

    operator fun contains(value: DpCoordinates): Boolean {
        val containsX = value.x in (center.x - 20.dp)..(center.x + 20.dp)
        val containsY = value.y in (center.y - 20.dp)..(center.y + 20.dp)
        return containsX && containsY
    }

    fun copyWeaponAware(
        position: DpCoordinates = this.position,
        turned: CharacterTurned = this.turned,
        isFighting: Boolean = this.isFighting,
        isMoving: Boolean = this.isMoving,
        stepX: Dp = this.stepX,
        stepY: Dp = this.stepY,
        image: ImageBitmap = this.image,
        weapon: Weapon = this.weapon.copy(position = this.position + weaponCorrectionDifference)
    ): MainHero {
        return this.copy(
            position = position,
            turned = turned,
            isFighting = isFighting,
            isMoving = isMoving,
            stepX = stepX,
            stepY = stepY,
            image = image,
            weapon = weapon
        )
    }
}

