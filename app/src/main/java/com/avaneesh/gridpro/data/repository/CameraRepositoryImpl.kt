package com.avaneesh.gridpro.data.repository

import android.app.Application
import androidx.camera.view.LifecycleCameraController
import com.avaneesh.gridpro.domain.repository.CameraRepository
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
    private val application: Application
): CameraRepository {

    override suspend fun takePhoto(controller: LifecycleCameraController) {
        TODO("Not yet implemented")
    }

    override suspend fun recordVideo(controller: LifecycleCameraController) {
        TODO("Not yet implemented")
    }

}