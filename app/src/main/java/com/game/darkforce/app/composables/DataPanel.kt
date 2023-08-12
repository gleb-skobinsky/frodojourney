package com.game.darkforce.app.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.game.darkforce.R

@Composable
fun RiddlesInterface(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit = {}) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.interface_sw),
            contentDescription = "Rectangle 19",
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(5.dp))
                .border(
                    border = BorderStroke(1.dp, Color(0xffc90e10)),
                    shape = RoundedCornerShape(5.dp)
                ),
            contentScale = ContentScale.FillWidth
        )
        content()
    }
}

@Preview(widthDp = 200, heightDp = 360)
@Composable
private fun Group52Preview() {
    RiddlesInterface(Modifier)
}