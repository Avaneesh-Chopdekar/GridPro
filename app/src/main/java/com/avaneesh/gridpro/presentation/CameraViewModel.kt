package com.avaneesh.gridpro.presentation

import androidx.camera.view.LifecycleCameraController
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avaneesh.gridpro.domain.repository.CameraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraRepository: CameraRepository
): ViewModel() {

    private val _isRecording = MutableStateFlow(false)
    val isRecording = _isRecording.asStateFlow()

    private val _selectedGrid = MutableStateFlow("Rule of Thirds")
    val selectedGrid = _selectedGrid.asStateFlow()

    private val _customGridLines = MutableStateFlow(listOf<Pair<Offset, Offset>>())
    val customGridLines = _customGridLines.asStateFlow()

    // Function to change the selected grid
    fun selectGrid(grid: String) {
        _selectedGrid.value = grid
    }

    fun updateCustomGridLines(lines: List<Pair<Offset, Offset>>) {
        _customGridLines.value = lines
    }

    fun clearCustomGridLines() {
        _customGridLines.value = emptyList()
    }

    fun onTakePhoto(
        controller: LifecycleCameraController
    ) {
        viewModelScope.launch {
            cameraRepository.takePhoto(controller)
        }
    }

    fun onRecordVideo(
        controller: LifecycleCameraController
    ) {
        _isRecording.update { !isRecording.value }

        viewModelScope.launch {
            cameraRepository.recordVideo(controller)
        }
    }
}