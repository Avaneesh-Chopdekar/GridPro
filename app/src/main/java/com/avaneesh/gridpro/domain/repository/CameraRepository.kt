package com.avaneesh.gridpro.domain.repository

import androidx.camera.view.LifecycleCameraController

interface CameraRepository {

    suspend fun takePhoto(
        controller: LifecycleCameraController
    )
    suspend fun recordVideo(
        controller: LifecycleCameraController
    )
}