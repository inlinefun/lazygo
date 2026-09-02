package com.github.inlinefun.lazygo.composables.screens.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.util.LazyGOTheme
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapContent() {
    if (LocalInspectionMode.current) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Maps functionality is disabled in preview mode"
            )
        }
    } else {
        val properties = MapProperties(
            mapType = MapType.NORMAL,
        )
        val uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
        )
        GoogleMap(
            mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
            properties = properties,
            uiSettings = uiSettings,
            modifier = Modifier
                .fillMaxSize()
        ) {

        }
    }
}

@Preview
@Composable
private fun PreviewMapContent() {
    LazyGOTheme {
        MapContent()
    }
}
