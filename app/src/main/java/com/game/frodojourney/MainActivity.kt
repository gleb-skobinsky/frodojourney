package com.game.frodojourney

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.game.frodojourney.app.App
import com.game.frodojourney.app.character.WeaponsResources
import com.game.frodojourney.app.character.enemies.TrooperShootingDown
import com.game.frodojourney.app.character.enemies.TrooperShootingToSide
import com.game.frodojourney.app.character.enemies.TrooperShootingToTop
import com.game.frodojourney.app.character.enemies.TrooperStanding
import com.game.frodojourney.app.character.mainCharacter.Luke
import com.game.frodojourney.app.character.mainCharacter.LukeRun
import com.game.frodojourney.app.map.MapResources
import com.game.frodojourney.ui.theme.FrodoJourneyTheme
import com.game.frodojourney.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {
    private val gameViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareResources(resources)
        setContent {
            FrodoJourneyTheme {
                App(gameViewModel)
            }
        }
    }
}

private fun prepareResources(res: Resources) {
    LukeRun.load(res)
    Luke.load(res)
    MapResources.load(res)
    WeaponsResources.load(res)
    TrooperStanding.load(res)
    TrooperShootingDown.load(res)
    TrooperShootingToSide.load(res)
    TrooperShootingToTop.load(res)
}