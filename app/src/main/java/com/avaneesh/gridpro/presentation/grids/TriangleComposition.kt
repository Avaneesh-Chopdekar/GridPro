package com.avaneesh.gridpro.presentation.grids

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
@Composable
fun TriangleComposition(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Draw diagonal lines
        drawLine(
            color = Color.White,
            start = Offset(0f, 0f),
            end = Offset(width, height),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.White,
            start = Offset(width, 0f),
            end = Offset(0f, height),
            strokeWidth = 2f
        )

        // Draw triangle from bottom left to top middle and bottom right
        drawLine(
            color = Color.White,
            start = Offset(0f, height),
            end = Offset(width / 2f, 0f),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.White,
            start = Offset(width, height),
            end = Offset(width / 2f, 0f),
            strokeWidth = 2f
        )
    }
}
