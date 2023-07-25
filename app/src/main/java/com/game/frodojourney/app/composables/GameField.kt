package com.game.frodojourney.app.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.app.character.enemies.Bullet
import com.game.frodojourney.app.character.enemies.FixedSizeSquad
import com.game.frodojourney.app.character.enemies.bulletSpeed
import com.game.frodojourney.app.character.enemies.generateId
import com.game.frodojourney.app.character.mainCharacter.MainHero
import com.game.frodojourney.viewmodel.MainViewModel
import com.game.frodojourney.viewmodel.MapState
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Math.toRadians
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun GamePlayingField(
    viewData: ViewData,
    viewModel: MainViewModel,
    mapState: MapState,
    character: MainHero,
    squad: FixedSizeSquad
) {
    val configuration = LocalConfiguration.current
    val objectsToDraw = remember(mapState.map.objects, character) {
        with(viewData) {
            mapState.map.objects.filter { it isLowerThan character }
        }
    }
    val bullets = remember {
        mutableStateMapOf<String, Bullet>()
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (viewData.size == Size.Zero) {
            viewModel.setViewData(
                ViewData(
                    density = Density(density),
                    size = size
                )
            )
            viewModel.updateOrientation(configuration.orientation)
        } else if (viewData.size != size) {
            viewModel.setViewData(viewData.copy(size = size))
            viewModel.updateOrientation(
                configuration.orientation
            )
        }
        with(mapState.map) {
            draw(viewData)
        }

        with(character) {
            draw(viewData)
        }

        for (trooper in squad) {
            with(trooper) {
                draw(viewData)
            }
        }


        with(mapState.map) {
            drawFrontObjects(viewData)
            drawObjects(objectsToDraw, viewData)
        }


        for (bullet in bullets.values) {
            with(bullet) {
                draw(viewData)
            }
        }

    }
    LaunchedEffect(
        key1 = squad.trooper1.isAlarmed,
        key2 = squad.trooper1.aimingDirection
    ) {
        while (squad.trooper1.isAlarmed) {
            val images = squad.trooper1.aiming.toImages()
            images.forEachIndexed { i, frame ->
                if (i == 4) {
                    val angle = squad.trooper1.aimingDirection
                    val bullet = Bullet(squad.trooper1.center, angle)
                    val id = generateId()
                    bullets[id] = bullet
                    val update = calculateBulletUpdate(angle)
                    launch(NonCancellable) {
                        sendBullet(
                            id = id,
                            bullets = bullets,
                            update = update,
                            viewModel = viewModel,
                            resistible = true
                        )
                        bullets.remove(id)
                    }
                }
                val newTrooper = squad.trooper1.copy(image = frame)
                viewModel.setTrooper1(newTrooper)
                delay(100L)
            }
        }
    }
}


private suspend fun sendBullet(
    id: String,
    bullets: SnapshotStateMap<String, Bullet>,
    update: Offset,
    viewModel: MainViewModel,
    resistible: Boolean
) {
    for (bulletStep in 0..100) {
        val oldBullet = bullets.getValue(id)
        val oldPos = oldBullet.position
        val newPos = oldPos.copy(
            x = oldPos.x + update.x.dp,
            y = oldPos.y + update.y.dp
        )
        if (resistible && newPos in viewModel.character.value) {
            bullets.remove(id)
            if (viewModel.character.value.isFighting) {
                resistBullet(oldBullet, bullets, update, viewModel)
            } else {
                viewModel.minusHp()
            }
            break
        }
        bullets[id] = oldBullet.copy(
            position = newPos
        )
        awaitFrame()
    }
    bullets.remove(id)
}

private suspend fun resistBullet(
    oldBullet: Bullet,
    bullets: SnapshotStateMap<String, Bullet>,
    update: Offset,
    viewModel: MainViewModel
) {
    val newBullet = oldBullet.copy(rotation = oldBullet.rotation.mirror())
    val newId = generateId()
    bullets[newId] = newBullet
    sendBullet(
        id = newId,
        bullets = bullets,
        update = update.copy(x = update.x * -1),
        viewModel = viewModel,
        resistible = false
    )
}

fun Float.mirror(): Float {
    val rem = this % 90
    return this - (rem * 2)
}

fun calculateBulletUpdate(angle: Float): Offset {
    val (xNeg, yNeg) = angle.checkIfObtuse()
    val angleRad = toRadians(angle.toDouble())
    val y = sin(angleRad) * bulletSpeed
    val x = sqrt(bulletSpeed.pow(2) - y.pow(2))
    return Offset((xNeg * x).toFloat(), (yNeg * y).toFloat())
}

fun Float.checkIfObtuse() = when (this) {
    in 90f..270f -> -1 to 1
    else -> 1 to 1
}

