package com.game.frodojourney.app.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.game.frodojourney.app.canvas.ViewData
import com.game.frodojourney.app.character.WeaponsResources
import com.game.frodojourney.app.character.enemies.Bullet
import com.game.frodojourney.app.character.enemies.FixedSizeSquad
import com.game.frodojourney.app.character.enemies.bulletSpeed
import com.game.frodojourney.app.character.enemies.generateId
import com.game.frodojourney.app.character.mainCharacter.MainHero
import com.game.frodojourney.viewmodel.MainViewModel
import com.game.frodojourney.viewmodel.MapState
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

        with(viewData) {
            for (bullet in bullets.values) {
                drawBullet(
                    offset = bullet.position.toOffset(),
                    rotation = squad.trooper1.aimingDirection
                )
            }
        }
    }
    LaunchedEffect(
        key1 = squad.trooper1.isAlarmed,
        key2 = squad.trooper1.aiming,
        key3 = character.position
    ) {
        if (squad.trooper1.isAlarmed) {
            val images = squad.trooper1.aiming.toImages()
            images.forEachIndexed { i, frame ->
                if (i == 4) {
                    val bullet = Bullet(squad.trooper1.center)
                    val id = generateId()
                    bullets[id] = bullet
                    val angle = squad.trooper1.aimingDirection
                    launch {
                        for (bulletStep in 0..100) {
                            val oldPos = bullets.getValue(id).position
                            val update = calculateBulletUpdate(angle)
                            bullets[id] =
                                Bullet(
                                    oldPos.copy(
                                        x = oldPos.x + update.x.dp,
                                        y = oldPos.y + update.y.dp
                                    )
                                )
                            awaitFrame()
                        }
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

fun calculateBulletUpdate(angle: Float): Offset {
    val (xNeg, yNeg) = angle.checkIfObtuse()
    val angleRad = toRadians(angle.toDouble())
    val y = sin(angleRad) * bulletSpeed
    val x = sqrt(bulletSpeed.pow(2) - y.pow(2))
    return Offset((xNeg * x).toFloat(), (yNeg * y).toFloat())
}

fun Float.checkIfObtuse() = when (this) {
    in 0f..90f -> 1 to 1
    in 90f..180f -> -1 to 1
    in 180f..270f -> -1 to 1
    in 270f..360f -> 1 to 1
    else -> 1 to 1
}

fun DrawScope.drawBullet(
    offset: Offset,
    rotation: Float
) {
    rotate(rotation, pivot = offset) {
        drawImage(
            image = WeaponsResources.bullet,
            topLeft = offset,
        )
    }
}