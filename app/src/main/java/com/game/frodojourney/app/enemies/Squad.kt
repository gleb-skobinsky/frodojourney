package com.game.frodojourney.app.enemies

data class Squad(val troopers: List<Enemy>) {
    constructor(vararg troopers: Enemy) : this(troopers.toList())

    operator fun iterator() = object : Iterator<Enemy> {
        private var i = 0
        override fun hasNext(): Boolean = i < troopers.size

        override fun next(): Enemy = troopers[i++]
    }
}