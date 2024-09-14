package com.avaneesh.gridpro.presentation.grids

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun GoldenRatio(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val phi = 1.61803398875
        val width = size.width
        val height = size.height

        val goldenWidth = (width / phi).toFloat()
        val goldenHeight = (height / phi).toFloat()

        val gridLines = listOf(
            Rect(0f, 0f, goldenWidth, height),
            Rect(width - goldenWidth, 0f, goldenWidth, height),
            Rect(0f, 0f, width, goldenHeight),
            Rect(0f, height - goldenHeight, width, goldenHeight)
        )

        val path = Path()
        for (rect in gridLines) {
            path.moveTo(rect.left, rect.top)
            path.lineTo(rect.right, rect.top)
            path.lineTo(rect.right, rect.bottom)
            path.lineTo(rect.left, rect.bottom)
            path.close()

        }

        drawPath(path = path, color = Color.White, style = Stroke(2f))
    }
}
