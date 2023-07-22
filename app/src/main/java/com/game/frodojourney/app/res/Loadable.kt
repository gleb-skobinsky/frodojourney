package com.game.frodojourney.app.res

import android.content.res.Resources
import androidx.compose.ui.unit.Density

interface Loadable {
    fun load(resources: Resources, density: Density)
}