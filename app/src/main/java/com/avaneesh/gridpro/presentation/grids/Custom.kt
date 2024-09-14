package com.avaneesh.gridpro.presentation.grids

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.avaneesh.gridpro.presentation.CameraViewModel

@Composable
fun CustomGrid(cameraViewModel: CameraViewModel, modifier: Modifier = Modifier) {
    var currentStart by remember { mutableStateOf<Offset?>(null) }
    val customGridLines by cameraViewModel.customGridLines.collectAsState()

    Canvas(modifier = modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { startPoint ->
                    currentStart = startPoint
                },
                onDragEnd = {
                    currentStart = null
                },
                onDragCancel = {
                    currentStart = null
                },
                onDrag = { change, _ ->
                    val endPoint = change.position
                    currentStart?.let { start ->
                        val newLine = customGridLines + Pair(start, endPoint)
                        cameraViewModel.updateCustomGridLines(newLine) // Update ViewModel with new line
                        currentStart = endPoint
                    }
                }
            )
        }
    ) {
        // Draw user-defined lines
        for (line in customGridLines) {
            drawLine(
                color = Color.White,
                start = line.first,
                end = line.second,
                strokeWidth = 2f
            )
        }

        // Draw in-progress line while dragging
        currentStart?.let { start ->
            drawLine(
                color = Color.White,
                start = start,
                end = start, // This will get updated while dragging
                strokeWidth = 2f
            )
        }
    }
}
