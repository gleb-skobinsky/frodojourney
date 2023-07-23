package com.game.frodojourney.app.character.mainCharacter

import androidx.compose.ui.graphics.ImageBitmap
import com.game.frodojourney.R
import com.game.frodojourney.app.character.Animation

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
        R.drawable.ls_run_0,
        R.drawable.ls_run_1,
        R.drawable.ls_run_2,
        R.drawable.ls_run_3,
        R.drawable.ls_run_4,
        R.drawable.ls_run_5,
        R.drawable.ls_run_6,
        R.drawable.ls_run_7,
        R.drawable.ls_run_8
    )
}
