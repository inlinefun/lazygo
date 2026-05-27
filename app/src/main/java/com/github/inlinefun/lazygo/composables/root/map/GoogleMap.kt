package com.github.inlinefun.lazygo.composables.root.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.MapColorScheme
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.ktx.buildGoogleMapOptions

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
        val properties = MapProperties()
        GoogleMap(
            mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false
            ),
            properties = properties
        ) {

        }
    }
}
