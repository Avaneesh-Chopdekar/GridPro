package com.avaneesh.gridpro.presentation

import android.app.Activity
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.avaneesh.gridpro.MainActivity
import com.avaneesh.gridpro.presentation.grids.CenterComposition
import com.avaneesh.gridpro.presentation.grids.CustomGrid
import com.avaneesh.gridpro.presentation.grids.Diagonal
import com.avaneesh.gridpro.presentation.grids.GoldenRatio
import com.avaneesh.gridpro.presentation.grids.GoldenSpiral
import com.avaneesh.gridpro.presentation.grids.RuleOfThirds
import com.avaneesh.gridpro.presentation.grids.TriangleComposition

@Composable
fun CameraScreen(
    activity: Activity
) {
    val controller = remember {
        LifecycleCameraController(
            activity.applicationContext
        ).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                CameraController.VIDEO_CAPTURE
            )
        }
    }

    val cameraViewModel = hiltViewModel<CameraViewModel>()
    val isRecording by cameraViewModel.isRecording.collectAsState()

    val selectedGrid by cameraViewModel.selectedGrid.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            }
        )

        when (selectedGrid) {
            "Rule of Thirds" -> RuleOfThirds()
            "Golden Ratio" -> GoldenRatio()
            "Golden Spiral" -> GoldenSpiral()
            "Diagonal" -> Diagonal()
            "Triangle" -> TriangleComposition()
            "Center" -> CenterComposition()
            "Custom" -> CustomGrid(cameraViewModel)
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GridSelector(onGridSelected = { grid ->
                cameraViewModel.selectGrid(grid)
            })

            Row {
                if (selectedGrid == "Custom") {
                    Box(
                        modifier = Modifier
                            .padding(end = 18.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .size(45.dp)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {
                                if (selectedGrid == "Custom") {
                                    cameraViewModel.clearCustomGridLines()
                                } else {
                                    Toast
                                        .makeText(
                                            activity.applicationContext,
                                            "Only Works With Custom Grid",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Custom Grid",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .size(45.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable {
                            controller.cameraSelector =
                                if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                    CameraSelector.DEFAULT_FRONT_CAMERA
                                } else {
                                    CameraSelector.DEFAULT_BACK_CAMERA
                                }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Cameraswitch,
                        contentDescription = "Switch Camera Preview",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        if ((activity as MainActivity).arePermissionsGranted()) {
                            cameraViewModel.onRecordVideo(controller)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector =
                    if(isRecording) Icons.Default.Stop
                    else Icons.Default.Videocam,
                    contentDescription = "Record Video",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(26.dp)
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        if ((activity as MainActivity).arePermissionsGranted()) {
                            cameraViewModel.onTakePhoto(controller)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Take Photo",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }



}