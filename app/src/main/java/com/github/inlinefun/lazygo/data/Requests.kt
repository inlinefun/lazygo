package com.github.inlinefun.lazygo.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.DirectionsWalk
import androidx.compose.material.icons.rounded.DriveEta
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.inlinefun.lazygo.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteRequest(
    @SerialName("origin")
    val origin: RequestPoint,
    @SerialName("destination")
    val destination: RequestPoint,
    @SerialName("intermediates")
    val intermediates: List<RequestPoint>,
    @SerialName("travelMode")
    val travelMode: TravelModes,
    @SerialName("routingPreference")
    val routingPreference: RoutingPreferences
) {
    companion object {
        fun new(
            origin: RequestPoint,
            destination: RequestPoint,
            intermediates: List<RequestPoint>,
            travelMode: TravelModes = TravelModes.DRIVE,
            routingPreference: RoutingPreferences = RoutingPreferences.TRAFFIC_AWARE
        ) = RouteRequest(
            origin = origin,
            destination = destination,
            intermediates = intermediates,
            travelMode = travelMode,
            routingPreference = routingPreference
        )
    }
}

@Serializable
data class RequestPoint(
    @SerialName("location")
    val location: RequestLocation
)

@Serializable
data class RequestLocation(
    @SerialName("latLng")
    val position: RequestLatLng
)

@Serializable
data class RequestLatLng(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double
)

@Serializable
enum class TravelModes(
    val resourceId: Int,
    val icon: ImageVector
) {
    @SerialName("DRIVE")
    DRIVE(
        resourceId = R.string.type_drive,
        icon = Icons.Rounded.DriveEta
    ),
    @SerialName("WALK")
    WALK(
        resourceId = R.string.type_walk,
        icon = Icons.AutoMirrored.Rounded.DirectionsWalk
    );
}

@Serializable
enum class RoutingPreferences {
    @SerialName("TRAFFIC_AWARE")
    TRAFFIC_AWARE,
    @SerialName("TRAFFIC_UNAWARE")
    TRAFFIC_UNAWARE;
}
