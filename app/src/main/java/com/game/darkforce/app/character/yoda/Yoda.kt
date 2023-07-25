package com.game.darkforce.app.character.yoda

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.game.darkforce.app.canvas.DpCoordinates
import com.game.darkforce.app.character.CharacterTurned
import com.game.darkforce.app.character.Weapon
import com.game.darkforce.app.character.mainCharacter.GenericCharacter

data class Yoda(
    override val position: DpCoordinates = DpCoordinates(1000.dp, 609.dp),
    override val image: ImageBitmap = YodaResources.skin,
    override val turned: CharacterTurned = CharacterTurned.RIGHT,
    override val isFighting: Boolean = false,
    override val isMoving: Boolean = false,
    override val weapon: Weapon = Weapon(),
) : GenericCharacter