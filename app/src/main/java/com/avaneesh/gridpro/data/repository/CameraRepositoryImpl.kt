package com.avaneesh.gridpro.data.repository

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.core.content.ContextCompat
import com.avaneesh.gridpro.domain.repository.CameraRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
    private val application: Application
): CameraRepository {

    private var recording: Recording? = null
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

    @SuppressLint("MissingPermission")
    override suspend fun recordVideo(controller: LifecycleCameraController) {
        if (recording != null) {
            recording?.stop()
            recording = null
            return
        }

        val timeInMillis = System.currentTimeMillis()
        val file = File(application.filesDir, "${timeInMillis}_video.mp4")
        recording = controller.startRecording(
            FileOutputOptions.Builder(file).build(),
            AudioConfig.create(true),
            ContextCompat.getMainExecutor(application)
        ) { event ->
            if (event is VideoRecordEvent.Finalize) {
                if (event.hasError()) {
                    recording?.close()
                    recording = null
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        saveVideo(file)
                    }
                }
            }
        }
    }

}