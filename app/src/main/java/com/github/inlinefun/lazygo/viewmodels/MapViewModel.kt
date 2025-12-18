package com.github.inlinefun.lazygo.viewmodels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.inlinefun.lazygo.data.API
import com.github.inlinefun.lazygo.data.RouteRequest
import com.github.inlinefun.lazygo.data.RouteStatus
import com.github.inlinefun.lazygo.util.asRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.Locale

class MapViewModel(
    context: Application
): AndroidViewModel(
    application = context
) {

    private val _focusedPosition = MutableStateFlow<LatLng?>(null)
    private val _focusedAddress = MutableStateFlow<String?>(null)
    private val _routeStatus = MutableStateFlow(RouteStatus.INACTIVE)
    private val _checkpoints = mutableStateListOf<LatLng>()
    private val _points = MutableStateFlow(emptyList<LatLng>())
    private val _routeDistance = MutableStateFlow(0)
    private val _failed = MutableStateFlow(false)

    val focusedPosition = _focusedPosition.asStateFlow()
    val address = _focusedAddress.asStateFlow()
    val routeStatus = _routeStatus.asStateFlow()
    val checkpoints: List<LatLng>
        get() = _checkpoints
    val points = _points.asStateFlow()
    val distance = _routeDistance.asStateFlow()
    val failed = _failed.asStateFlow()

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

    suspend fun buildRoute() {
        if (checkpoints.size < 2) {
            _points.value = emptyList()
            return
        }
        val origin = checkpoints.first().asRequest()
        val destination = checkpoints.last().asRequest()
        // both are data classes, therefore this check is relevant
        if (origin == destination) {
            return
        }
        val request = RouteRequest.new(origin, destination)
        try {
            val response = API.routes.getRoute(request)
            val route = response.routes[0]
            val encodedPolyline = route.polyline.encodedPolyline
            _routeDistance.value = route.distanceMeters
            _points.value = PolyUtil.decode(encodedPolyline)
            _failed.value = false
        } catch (e: HttpException) {
            Log.e(
                "RoutesAPI",
                e.response()?.errorBody()?.string() ?: "no error body"
            )
            _points.value = checkpoints
            _failed.value = true
        } catch (e: Exception) {
            throw e
        }
    }

    fun updateFocusedLocation(
        position: LatLng,
        context: Context
    ) {
        _focusedPosition.value = position
        updateAddress(context)
    }

    fun updateAddress(context: Context) {
        focusedPosition.value?.let { location ->
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val geocode = Geocoder(context, Locale.getDefault())
                    val results = geocode.getFromLocation(location.latitude, location.longitude, 1)
                    val address = results
                        ?.firstOrNull()
                        ?.getAddressLine(0)
                    _focusedAddress.value = address
                } catch (_: Exception) {
                    _focusedAddress.value = null
                }
            }
        }
    }

    fun pauseRoute() {
        _routeStatus.value = RouteStatus.PAUSED
    }
    fun startRoute() {
        viewModelScope.launch {
            async {
                buildRoute()
            }.await()
            activateRoute()
        }
    }
    /**
     * Internal function to handle movement logic
     */
    private fun activateRoute() {
        _routeStatus.value = RouteStatus.ACTIVE
    }
    fun stopRoute() {
        _routeStatus.value = RouteStatus.INACTIVE
    }

    fun addCheckpoint(point: LatLng) {
        if (checkpoints.lastOrNull() == point) {
            return
        }
        viewModelScope.launch {
            val wasActive = routeStatus.value == RouteStatus.ACTIVE
            if (wasActive) {
                pauseRoute()
            }
            _checkpoints.add(point)
            async {
                buildRoute()
            }.await()
            if (wasActive) {
                activateRoute()
            }
        }
    }
    fun removeLastCheckpoint() {
        viewModelScope.launch {
            val wasActive = routeStatus.value == RouteStatus.ACTIVE
            if (wasActive) {
                pauseRoute()
            }
            if (checkpoints.isNotEmpty()) {
                _checkpoints.removeAt(checkpoints.size - 1)
            }
            async {
                buildRoute()
            }.await()
            if (wasActive) {
                activateRoute()
            }
        }
    }

}