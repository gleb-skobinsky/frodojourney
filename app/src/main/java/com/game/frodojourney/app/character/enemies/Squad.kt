package com.game.frodojourney.app.character.enemies

import androidx.compose.runtime.Stable

@Stable
data class Squad(val troopers: MutableList<Trooper>) {
    constructor(vararg troopers: Trooper) : this(troopers.toMutableList())

    operator fun iterator() = object : Iterator<Trooper> {
        private var i = 0
        override fun hasNext(): Boolean = i < troopers.size

        override fun next(): Trooper = troopers[i++]
    }

    val size = troopers.size
}