package com.github.inlinefun.lazygo.util

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

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
