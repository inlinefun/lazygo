package com.github.inlinefun.lazygo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(
    application
) {

    private val _focusedPosition = MutableStateFlow<LatLng?>(value = null)
    private val _tilt = MutableStateFlow(value = 0f)
    private val _zoom = MutableStateFlow(value = 0f)
    private val _bearing = MutableStateFlow(value = 0f)

    val focusedPosition = _focusedPosition.asStateFlow()
    val tilt = _tilt.asStateFlow()
    val zoom = _zoom.asStateFlow()
    val bearing = _bearing.asStateFlow()

    fun updateCameraPosition(position: CameraPosition) {
        _focusedPosition.value = position.target
        _tilt.value = position.tilt
        _zoom.value = position.zoom
        _bearing.value = position.bearing
    }

    fun updateCameraBearing(bearing: Float) {
        _bearing.value = bearing
    }

    fun toggleMapTilt() {
        _tilt.value = when (_tilt.value) {
            90f -> 0f
            0f -> 90f
            else -> 0f
        }
    }

    fun resetBearing() {
        _bearing.value = 0f
    }

}