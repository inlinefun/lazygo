package com.github.inlinefun.lazygo.viewmodels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.github.inlinefun.lazygo.data.RouteStatus
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModel(
    context: Application
): AndroidViewModel(
    application = context
) {

    private val _focusedPosition = MutableStateFlow<LatLng?>(null)
    private val _routeStatus = MutableStateFlow(RouteStatus.INACTIVE)
    private val _checkpoints = mutableStateListOf<LatLng>()

    val focusedPosition = _focusedPosition.asStateFlow()
    val routeStatus = _routeStatus.asStateFlow()
    val checkpoints: List<LatLng>
        get() = _checkpoints


    fun getLocation(
        context: Context,
        onSuccess: (Location) -> Unit,
        onError: (Exception) -> Unit
    ) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val client = LocationServices.getFusedLocationProviderClient(context)
            client.lastLocation
                .addOnSuccessListener { cached ->
                    cached?.let {
                        onSuccess(it)
                    } ?:
                    client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
                        .addOnSuccessListener(onSuccess)
                        .addOnFailureListener(onError)
                }
        } else {
            onError(IllegalStateException("Failed to access Location"))
        }
    }

    fun updateFocusedLocation(
        position: LatLng
    ) {
        _focusedPosition.value = position
    }

    fun pauseRoute() {
        _routeStatus.value = RouteStatus.PAUSED
    }
    fun activateRoute() {
        _routeStatus.value = RouteStatus.ACTIVE
    }
    fun stopRoute() {
        _routeStatus.value = RouteStatus.INACTIVE
    }

    fun addCheckpoint(point: LatLng) {
        _checkpoints.add(point)
    }
    fun removeLastCheckpoint() {
        if (checkpoints.isNotEmpty()) {
            _checkpoints.removeAt(checkpoints.size - 1)
        }
    }

}