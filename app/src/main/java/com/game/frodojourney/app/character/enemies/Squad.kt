package com.game.frodojourney.app.character.enemies


data class Squad(val troopers: MutableList<Trooper>) {
    constructor(vararg troopers: Trooper) : this(troopers.toMutableList())

    operator fun iterator() = object : Iterator<Trooper> {
        private var i = 0
        override fun hasNext(): Boolean = i < troopers.size

        override fun next(): Trooper = troopers[i++]
    }

    val size = troopers.size
}

data class FixedSquad(
    val trooper1: Trooper = Trooper.createDefault(
        472, 420
    ),
    val trooper2: Trooper = Trooper.createDefault(715, 132),
    val trooper3: Trooper = Trooper.createDefault(808, 472),
    val trooper4: Trooper = Trooper.createDefault(1104, 242),
    val trooper5: Trooper = Trooper.createDefault(727, 609)

) {
    operator fun iterator() = object : Iterator<Trooper> {
        private var i = 0
        override fun hasNext(): Boolean = i < 5

        override fun next(): Trooper =
            when (i) {
                0 -> {
                    i++; trooper1
                }
                1 -> {
                    i++; trooper2
                }
                2 -> {
                    i++; trooper3
                }
                3 -> {
                    i++; trooper4
                }
                4 -> {
                    i++; trooper5
                }
                else -> trooper1
            }
    }
}