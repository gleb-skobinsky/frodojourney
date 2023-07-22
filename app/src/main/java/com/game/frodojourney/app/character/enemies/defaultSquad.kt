package com.game.frodojourney.app.character.enemies

import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.DpCoordinates
import com.game.frodojourney.app.character.CharacterTurned

val defaultSquad =
    Squad(
        Trooper(
            position = DpCoordinates(x = 472.dp, y = 420.dp),
            image = EnemyResources.trooperImage,
            turned = CharacterTurned.LEFT
        ),
        Trooper(
            position = DpCoordinates(715.dp, 132.dp),
            image = EnemyResources.trooperImage,
            turned = CharacterTurned.LEFT
        ),
        Trooper(
            position = DpCoordinates(808.dp, 472.dp),
            image = EnemyResources.trooperImage,
            turned = CharacterTurned.LEFT
        ),
        Trooper(
            position = DpCoordinates(808.dp, 472.dp),
            image = EnemyResources.trooperImage,
            turned = CharacterTurned.LEFT
        ),
        Trooper(
            position = DpCoordinates(1104.dp, 242.dp),
            image = EnemyResources.trooperImage,
            turned = CharacterTurned.LEFT
        ),
        Trooper(
            position = DpCoordinates(727.dp, 609.dp),
            image = EnemyResources.trooperImage,
            turned = CharacterTurned.LEFT
        )
    )