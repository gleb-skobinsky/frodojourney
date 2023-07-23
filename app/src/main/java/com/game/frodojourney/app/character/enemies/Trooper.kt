package com.game.frodojourney.app.character.enemies

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.character.CharacterTurned
import com.game.frodojourney.app.character.Weapon
import com.game.frodojourney.app.character.WeaponsResources
import com.game.frodojourney.app.character.mainCharacter.GenericCharacter

data class Trooper(
    override val position: DpCoordinates,
    override val image: ImageBitmap,
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
    override val isMoving: Boolean = false,
    val isAlarmed: Boolean = false,
    override val weapon: Weapon = Weapon(DpCoordinates.Zero, WeaponsResources.largeRifle),
    override val isFighting: Boolean = false
) : GenericCharacter {

    companion object {
        fun createDefault(x: Int, y: Int) = Trooper(
            position = DpCoordinates(x = x.dp, y = y.dp),
            image = TrooperStanding.reset(),
            turned = CharacterTurned.RIGHT
        )
    }
}