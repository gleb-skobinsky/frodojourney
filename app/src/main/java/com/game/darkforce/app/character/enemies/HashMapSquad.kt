package com.game.darkforce.app.character.enemies

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap

typealias ImperialSquad = SnapshotStateMap<String, Trooper>

val hashSquad = mutableStateMapOf(
    "trooper1" to Trooper.createDefault(472, 350),
    "trooper2" to Trooper.createDefault(715, 132),
    "trooper3" to Trooper.createDefault(808, 472),
    "trooper4" to Trooper.createDefault(1104, 242),
    "trooper5" to Trooper.createDefault(727, 609)
)