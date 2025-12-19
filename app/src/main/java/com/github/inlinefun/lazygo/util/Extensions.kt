package com.github.inlinefun.lazygo.util

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import com.github.inlinefun.lazygo.data.RequestLatLng
import com.github.inlinefun.lazygo.data.RequestLocation
import com.github.inlinefun.lazygo.data.RequestPoint
import com.github.inlinefun.lazygo.ui.Constants
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

fun CameraPosition.copy(
    target: LatLng = this.target,
    zoom: Float = this.zoom,
    tilt: Float = this.tilt,
    bearing: Float = this.bearing
): CameraPosition {
    return CameraPosition(
        target,
        zoom,
        tilt,
        bearing
    )
}

fun Location.asLatLng() = LatLng(latitude, longitude)

fun Location.asCameraUpdate(
    state: CameraPositionState
): CameraUpdate {
    return CameraUpdateFactory.newCameraPosition(
        CameraPosition(
            this.asLatLng(),
            state.position.zoom.takeIf { value ->
                Constants.MapZoom.IDEAL.let { ideal ->
                    val range = 1.5f
                    value >= ideal - range && value <= ideal + range
                }
            } ?: Constants.MapZoom.IDEAL,
            state.position.tilt,
            state.position.bearing
        )
    )
}

suspend fun CameraPositionState.to(
    location: Location
) {
    this.animate(location.asCameraUpdate(this), Constants.Durations.MAP_MOVEMENTS)
}

fun Context.getApiKey(): String {
    return this.packageManager
        .getApplicationInfo(
            this.packageName,
            PackageManager.GET_META_DATA
        )
        .metaData
        .getString("routes_api_key")
        ?: error("Missing API Key from manifest")
}

fun LatLng.asRequest(): RequestPoint {
    return RequestPoint(
        location = RequestLocation(
            position = RequestLatLng(
                latitude = latitude,
                longitude = longitude
            )
        )
    )
}

fun Enum<*>.displayName(): String {
    return name.lowercase().replaceFirstChar { it.uppercase() }
}
