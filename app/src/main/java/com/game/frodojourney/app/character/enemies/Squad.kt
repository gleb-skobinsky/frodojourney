package com.game.frodojourney.app.character.enemies

import androidx.compose.runtime.Stable

@Stable
data class Squad(val troopers: List<Trooper>) {
    constructor(vararg troopers: Trooper) : this(troopers.toList())

    operator fun iterator() = object : Iterator<Trooper> {
        private var i = 0
        override fun hasNext(): Boolean = i < troopers.size

        override fun next(): Trooper = troopers[i++]
    }
}