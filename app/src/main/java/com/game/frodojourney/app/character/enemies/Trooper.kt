package com.game.frodojourney.app.character.enemies

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.character.CharacterTurned
import com.game.frodojourney.app.character.Weapon
import com.game.frodojourney.app.character.WeaponsResources
import com.game.frodojourney.app.character.mainCharacter.GenericCharacter
import kotlinx.coroutines.Job

data class Trooper(
    override val position: DpCoordinates,
    override val image: ImageBitmap,
    val imageIndex: Int = 0,
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
    val aiming: TrooperAim = TrooperAim.DOWN,
    override val isMoving: Boolean = false,
    override val weapon: Weapon = Weapon(DpCoordinates.Zero, WeaponsResources.largeRifle),
    override val isFighting: Boolean = false,
    var animationJob: Job? = null
) : GenericCharacter {

    companion object {
        fun createDefault(x: Int, y: Int) = Trooper(
            position = DpCoordinates(x = x.dp, y = y.dp),
            image = TrooperStanding.reset(),
            turned = CharacterTurned.RIGHT
        )
    }
}

enum class TrooperAim {
    UP,
    DOWN,
    SIDE;
    fun toImages(): List<ImageBitmap> = when (this) {
        UP -> TrooperShootingToTop.images
        SIDE -> TrooperShootingToSide.images
        DOWN -> TrooperShootingDown.images
    }
}