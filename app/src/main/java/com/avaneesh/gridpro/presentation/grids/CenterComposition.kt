package com.avaneesh.gridpro.presentation.grids

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
@Composable
fun CenterComposition(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val halfWidth = size.width / 2
        val halfHeight = size.height / 2

        // Vertical center line
        drawLine(
            color = Color.White,
            start = Offset(halfWidth, 0f),
            end = Offset(halfWidth, size.height),
            strokeWidth = 2f
        )

        // Horizontal center line
        drawLine(
            color = Color.White,
            start = Offset(0f, halfHeight),
            end = Offset(size.width, halfHeight),
            strokeWidth = 2f
        )
    }
}
