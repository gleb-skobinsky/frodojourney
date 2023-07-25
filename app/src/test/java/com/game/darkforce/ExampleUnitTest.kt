package com.game.darkforce

import androidx.compose.ui.unit.dp
import com.game.darkforce.app.canvas.DpCoordinates
import com.game.darkforce.app.canvas.distance
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class TestDistance {
    @Test
    fun distanceBetweenTwoPoints() {
        val pos1 = DpCoordinates(3.dp, 4.dp)
        val pos2 = DpCoordinates(7.dp, 1.dp)
        val distance = distance(pos1, pos2)
        assertEquals(distance, 5f, 0.001f)
    }
}