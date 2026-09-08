package com.github.inlinefun.lazygo.composables.screens.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.github.inlinefun.lazygo.composables.components.map.LazyGoogleMap
import com.github.inlinefun.lazygo.composables.components.map.LazyMapOverlay
import com.github.inlinefun.lazygo.data.map.MapViewModel
import com.github.inlinefun.lazygo.util.LazyGOTheme
import com.github.inlinefun.lazygo.util.ScreenWrapper
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapContent() {
    if (LocalInspectionMode.current) {
        ScreenWrapper {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Maps functionality is disabled in preview mode"
                )
            }
        }
    } else {
        RealMapContent()
    }
}

/**
 * very real
 */
@Composable
private fun RealMapContent() {
    val mapViewModel = hiltViewModel<MapViewModel>()
    val checkpoints = mapViewModel.checkpoints
    val cameraPositionState = rememberCameraPositionState()
    LazyGoogleMap(
        cameraPositionState = cameraPositionState,
        markers = checkpoints
    )
    LazyMapOverlay(
        addPoint = {
            mapViewModel.addPoint(cameraPositionState.position.target)
        },
        removeLastPoint = mapViewModel::removeLastPoint
    )
}

@Preview
@Composable
private fun PreviewMapContent() {
    LazyGOTheme {
        MapContent()
    }
}
