package com.game.darkforce.app.character.mainCharacter

import androidx.compose.ui.graphics.ImageBitmap
import com.game.darkforce.R
import com.game.darkforce.app.character.Animation

object Luke : Animation() {
    override val ids: List<Int> = listOf(
        R.drawable.lc_calm_no_ls
    )

    fun removeWeapon(): ImageBitmap {
        return images[0]
    }
}

object LukeRun : Animation() {
    override val ids: List<Int> = listOf(
        R.drawable.lc_calm,
        R.drawable.ls_run_a0,
        R.drawable.ls_run_a1,
        R.drawable.ls_run_a2,
        R.drawable.ls_run_a3,
        R.drawable.ls_run_a4,
        R.drawable.ls_run_a5,
        R.drawable.ls_run_a6,
        R.drawable.ls_run_a7,
        R.drawable.ls_run_a8
    )
}
