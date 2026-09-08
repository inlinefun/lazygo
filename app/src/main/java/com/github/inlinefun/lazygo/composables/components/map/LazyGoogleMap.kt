package com.github.inlinefun.lazygo.composables.components.map

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.github.inlinefun.lazygo.data.preferences.LazyPreferences
import com.github.inlinefun.lazygo.data.preferences.PreferenceAppTheme
import com.github.inlinefun.lazygo.data.preferences.PreferenceMapTheme
import com.github.inlinefun.lazygo.data.preferences.PreferenceMapType
import com.github.inlinefun.lazygo.data.preferences.getPreferenceAsState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun LazyGoogleMap(
    cameraPositionState: CameraPositionState,
    markers: List<LatLng>,
) {
    val context = LocalContext.current
    val appTheme by context.getPreferenceAsState(
        preference = LazyPreferences.Appearance.appTheme
    )
    val mapType by context.getPreferenceAsState(
        preference = LazyPreferences.Appearance.mapType
    )
    val mapTheme by context.getPreferenceAsState(
        preference = LazyPreferences.Appearance.mapTheme
    )
    val properties = MapProperties(
        mapType = when (mapType) {
            PreferenceMapType.TERRAIN -> MapType.TERRAIN
            else -> MapType.NORMAL
        }
    )
    val mapColorScheme = when (mapTheme) {
        PreferenceMapTheme.SYSTEM_DEFAULT -> ComposeMapColorScheme.FOLLOW_SYSTEM
        PreferenceMapTheme.LIGHT -> ComposeMapColorScheme.LIGHT
        PreferenceMapTheme.DARK -> ComposeMapColorScheme.DARK
        PreferenceMapTheme.FOLLOW_APP -> when (appTheme) {
            PreferenceAppTheme.SYSTEM_DEFAULT -> {
                if (isSystemInDarkTheme()) {
                    ComposeMapColorScheme.DARK
                } else {
                    ComposeMapColorScheme.LIGHT
                }
            }

            PreferenceAppTheme.DARK -> ComposeMapColorScheme.DARK
            PreferenceAppTheme.LIGHT -> ComposeMapColorScheme.LIGHT
        }
    }
    val uiSettings = MapUiSettings(
        zoomControlsEnabled = false,
    )
    GoogleMap(
        mapColorScheme = mapColorScheme,
        properties = properties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPositionState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        markers.forEach { marker ->
            val state = rememberUpdatedMarkerState(position = marker)
            Marker(
                state = state
            )
        }
    }
}
