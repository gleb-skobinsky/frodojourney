package com.game.frodojourney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.game.frodojourney.app.App
import com.game.frodojourney.ui.theme.FrodoJourneyTheme
import com.game.frodojourney.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {
    private val gameViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrodoJourneyTheme {
                App(gameViewModel)
            }
        }
    }
}