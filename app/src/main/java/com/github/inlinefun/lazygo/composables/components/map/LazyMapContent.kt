package com.github.inlinefun.lazygo.composables.components.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.github.inlinefun.lazygo.data.preferences.LazyPreferences
import com.github.inlinefun.lazygo.data.preferences.PreferenceMapTheme
import com.github.inlinefun.lazygo.data.preferences.PreferenceMapType
import com.github.inlinefun.lazygo.data.preferences.getPreferenceAsState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

@Composable
fun LazyMapContent() {
    val context = LocalContext.current
    val mapTypePref by context.getPreferenceAsState(
        preference = LazyPreferences.Appearance.mapType
    )
    val mapTheme by context.getPreferenceAsState(
        preference = LazyPreferences.Appearance.mapTheme
    )
    val properties = MapProperties(
        mapType = when(mapTypePref) {
            PreferenceMapType.TERRAIN -> MapType.TERRAIN
            else -> MapType.NORMAL
        }
    )
    val mapColorScheme = when(mapTheme) {
        PreferenceMapTheme.SYSTEM_DEFAULT -> ComposeMapColorScheme.FOLLOW_SYSTEM
        PreferenceMapTheme.LIGHT -> ComposeMapColorScheme.LIGHT
        PreferenceMapTheme.DARK -> ComposeMapColorScheme.DARK
    }
    val uiSettings = MapUiSettings(
        zoomControlsEnabled = false,
    )
    GoogleMap(
        mapColorScheme = mapColorScheme,
        properties = properties,
        uiSettings = uiSettings,
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}
