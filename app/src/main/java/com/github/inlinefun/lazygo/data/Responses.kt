package com.github.inlinefun.lazygo.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteResponse(
    @SerialName("routes")
    val routes: List<RouteData>
)

@Serializable
data class RouteData(
    @SerialName("distanceMeters")
    val distanceMeters: Int,
    @SerialName("polyline")
    val polyline: EncodedPolyline
)

@Serializable
data class EncodedPolyline(
    @SerialName("encodedPolyline")
    val encodedPolyline: String
)
