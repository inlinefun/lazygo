package com.github.inlinefun.lazygo.composables.root.map

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.github.inlinefun.lazygo.preferences.AppTheme
import com.github.inlinefun.lazygo.preferences.MapTheme
import com.github.inlinefun.lazygo.preferences.MapType
import com.github.inlinefun.lazygo.preferences.Preferences
import com.github.inlinefun.lazygo.preferences.getPreferenceAsState
import com.github.inlinefun.lazygo.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.flow.filter
import com.google.maps.android.compose.MapType as GoogleMapType

@Composable
fun GoogleMap(
    modifier: Modifier = Modifier
) {
    if (LocalInspectionMode.current) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
        )
    } else {
        val context = LocalContext.current
        val appTheme by context
            .getPreferenceAsState(Preferences.Appearance.appTheme)
        val mapTheme by context
            .getPreferenceAsState(Preferences.Appearance.mapTheme)
        val mapType by context
            .getPreferenceAsState(Preferences.Appearance.mapType)
        val isSystemInDarkTheme = isSystemInDarkTheme()
        val mapColorScheme = remember(mapTheme, appTheme, isSystemInDarkTheme) {
            when (mapTheme) {
                MapTheme.FOLLOW_APP -> when (appTheme) {
                    AppTheme.SYSTEM_DEFAULT -> {
                        if (isSystemInDarkTheme) {
                            ComposeMapColorScheme.DARK
                        } else {
                            ComposeMapColorScheme.LIGHT
                        }
                    }

                    AppTheme.DARK -> ComposeMapColorScheme.DARK
                    AppTheme.LIGHT -> ComposeMapColorScheme.LIGHT
                }

                MapTheme.SYSTEM_DEFAULT -> ComposeMapColorScheme.FOLLOW_SYSTEM
                MapTheme.DARK -> ComposeMapColorScheme.DARK
                MapTheme.LIGHT -> ComposeMapColorScheme.LIGHT
            }
        }
        val googleMapType = remember(mapType) {
            when (mapType) {
                MapType.DEFAULT -> GoogleMapType.NORMAL
                MapType.TERRAIN -> GoogleMapType.TERRAIN
            }
        }
        val properties = MapProperties(
            mapType = googleMapType
        )

        var cameraAnimating by remember { mutableStateOf(false) }

        val mapViewModel = hiltViewModel<MapViewModel>()
        val bearing by mapViewModel.bearing.collectAsState()
        val tilt by mapViewModel.tilt.collectAsState()
        val checkpoints = mapViewModel.checkpoints

        val cameraPositionState = rememberCameraPositionState()
        LaunchedEffect(bearing, tilt) {
            if (bearing == cameraPositionState.position.bearing && tilt == cameraPositionState.position.tilt)
                return@LaunchedEffect
            val position = CameraPosition
                .builder(cameraPositionState.position)
                .bearing(bearing)
                .tilt(tilt)
                .build()

            CameraUpdateFactory
                .newCameraPosition(position)
                .let { cameraUpdate ->
                    cameraAnimating = true
                    cameraPositionState.animate(update = cameraUpdate, durationMs = 250)
                    cameraAnimating = false
                }
        }
        LaunchedEffect(cameraPositionState) {
            snapshotFlow { cameraPositionState.isMoving }
                .filter { moving ->
                    !moving
                }
                .collect {
                    mapViewModel.updateCameraPosition(cameraPositionState.position)
                }
        }
        LaunchedEffect(cameraPositionState) {
            snapshotFlow { cameraPositionState.position.bearing }
                .filter { !cameraAnimating }
                .collect {
                    mapViewModel.updateCameraBearing(it)
                }
        }
        GoogleMap(
            cameraPositionState = cameraPositionState,
            mapColorScheme = mapColorScheme,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                compassEnabled = false
            ),
            properties = properties
        ) {
            checkpoints
                .forEach { checkpoint ->
                    val state = rememberUpdatedMarkerState(position = checkpoint)
                    Marker(
                        state = state
                    )
                }
        }
    }
}
