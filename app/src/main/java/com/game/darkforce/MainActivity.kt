package com.game.darkforce

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.game.darkforce.app.App
import com.game.darkforce.app.character.WeaponsResources
import com.game.darkforce.app.character.enemies.TrooperDying
import com.game.darkforce.app.character.enemies.TrooperShootingDown
import com.game.darkforce.app.character.enemies.TrooperShootingDownSide
import com.game.darkforce.app.character.enemies.TrooperShootingSide
import com.game.darkforce.app.character.enemies.TrooperShootingUp
import com.game.darkforce.app.character.enemies.TrooperShootingUpSide
import com.game.darkforce.app.character.enemies.TrooperStanding
import com.game.darkforce.app.character.mainCharacter.Luke
import com.game.darkforce.app.character.mainCharacter.LukeRun
import com.game.darkforce.app.character.yoda.YodaResources
import com.game.darkforce.app.map.MapResources
import com.game.darkforce.ui.theme.DarkForceTheme
import com.game.darkforce.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {
    private val gameViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareResources(resources)
        setContent {
            DarkForceTheme {
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
    TrooperShootingUp.load(res)
    TrooperShootingUpSide.load(res)
    TrooperShootingSide.load(res)
    TrooperShootingDownSide.load(res)
    TrooperShootingDown.load(res)
    TrooperDying.load(res)
    YodaResources.load(res)
}