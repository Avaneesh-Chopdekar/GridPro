package com.avaneesh.gridpro.data.repository

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.video.Recording
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.avaneesh.gridpro.domain.repository.CameraRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
    private val application: Application
): CameraRepository {

    private val recording: Recording? = null
    override suspend fun takePhoto(controller: LifecycleCameraController) {
        controller.takePicture(
            ContextCompat.getMainExecutor(application),
            object : ImageCapture.OnImageCapturedCallback() {

                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    super.onCaptureSuccess(imageProxy)

                    val matrix = Matrix().apply {
                        postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
                    }

                    val imageBitmap = Bitmap.createBitmap(
                        imageProxy.toBitmap(),
                        0,0,
                        imageProxy.width,
                        imageProxy.height,
                        matrix,
                        true
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        saveImage(imageBitmap)
                    }
                }
            }
        )
    }

    override suspend fun recordVideo(controller: LifecycleCameraController) {
        TODO("Not yet implemented")
    }

}