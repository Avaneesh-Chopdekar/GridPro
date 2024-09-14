package com.avaneesh.gridpro.presentation.grids

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun RuleOfThirds(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val thirdWidth = width / 3
        val thirdHeight = height / 3

        // Draw vertical lines
        drawLine(
            color = Color.White,
            start = Offset(thirdWidth, 0f),
            end = Offset(thirdWidth, height),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.White,
            start = Offset(2 * thirdWidth, 0f),
            end = Offset(2 * thirdWidth, height),
            strokeWidth = 2f
        )

        // Draw horizontal lines
        drawLine(
            color = Color.White,
            start = Offset(0f, thirdHeight),
            end = Offset(width, thirdHeight),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.White,
            start = Offset(0f, 2 * thirdHeight),
            end = Offset(width, 2 * thirdHeight),
            strokeWidth = 2f
        )
    }
}
