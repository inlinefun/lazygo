package com.github.inlinefun.lazygo.composables.root.map

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import com.github.inlinefun.lazygo.preferences.AppTheme
import com.github.inlinefun.lazygo.preferences.MapTheme
import com.github.inlinefun.lazygo.preferences.MapType
import com.github.inlinefun.lazygo.preferences.Preferences
import com.github.inlinefun.lazygo.preferences.getPreferenceAsState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
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
        GoogleMap(
            mapColorScheme = mapColorScheme,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false
            ),
            properties = properties
        ) {

        }
    }
}
