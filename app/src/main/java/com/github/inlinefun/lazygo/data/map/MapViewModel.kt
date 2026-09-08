package com.github.inlinefun.lazygo.data.map

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    val checkpoints: List<LatLng>
        field = mutableStateListOf<LatLng>()

    fun addPoint(point: LatLng) {
        viewModelScope.launch {
            if (checkpoints.lastOrNull() != point) {
                checkpoints.add(point)
            }
        }
    }

    fun removeLastPoint() {
        viewModelScope.launch {
            checkpoints.removeLastOrNull()
        }
    }

}
