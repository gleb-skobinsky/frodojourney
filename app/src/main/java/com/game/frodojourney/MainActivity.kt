package com.game.frodojourney

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.game.frodojourney.app.App
import com.game.frodojourney.app.character.mainCharacter.LukeRun
import com.game.frodojourney.app.character.WeaponsResources
import com.game.frodojourney.app.character.enemies.EnemyResources
import com.game.frodojourney.app.map.MapResources
import com.game.frodojourney.ui.theme.FrodoJourneyTheme
import com.game.frodojourney.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {

    private val gameViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            prepareResources(resources, LocalDensity.current)
            FrodoJourneyTheme {
                App(gameViewModel)
            }
        }
    }
}

private fun prepareResources(res: Resources, density: Density) {
    LukeRun.load(res, density)
    MapResources.load(res, density)
    WeaponsResources.load(res, density)
    EnemyResources.load(res, density)
}