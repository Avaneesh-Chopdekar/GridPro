package com.avaneesh.gridpro.presentation.grids

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


// TODO: Make this work (It is broken)
@Composable
fun GoldenSpiral(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val phi = 1.61803398875f
        var theta = PI * 0.5

        var r = 1f // Adjust this value as needed
        val numTurns = 10

        for (i in 0 until numTurns * 4) {
            val x = r * cos(theta)
            val y = r * sin(theta)
            drawCircle(
                center = Offset(x.toFloat(), y.toFloat()),
                radius = 1f,
                color = Color.Black
            )
            r *= phi
            theta += PI / 2
        }
    }
}
