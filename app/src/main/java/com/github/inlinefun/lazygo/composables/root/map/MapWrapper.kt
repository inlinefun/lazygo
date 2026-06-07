package com.github.inlinefun.lazygo.composables.root.map

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.Constants
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.common.ScaffoldWrapper
import com.github.inlinefun.lazygo.composables.root.map.components.MapOverlayButton
import com.github.inlinefun.lazygo.viewmodel.MapViewModel

@Composable
fun MapWrapper(
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(
                    space = Constants.Spacing.medium,
                )
            ) {
                if (!LocalInspectionMode.current) {
                    val mapViewModel = hiltViewModel<MapViewModel>()
                    val tilt by mapViewModel
                        .tilt
                        .collectAsState()
                    val rotation by animateIntAsState(
                        targetValue = if (tilt == 0f) {
                            0
                        } else {
                            45
                        }
                    )
                    FloatingActionButton(
                        onClick = mapViewModel::toggleMapTilt
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.my_location),
                            contentDescription = null,
                            modifier = Modifier
                                .rotate(degrees = rotation.toFloat())
                        )
                    }
                }
                ExtendedFloatingActionButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.play_arrow),
                        contentDescription = null
                    )
                    Spacer(
                        modifier = Modifier
                            .width(Constants.Spacing.extraSmall)
                    )
                    Text(
                        text = stringResource(id = R.string.label_start),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets(),
        modifier = modifier
            .fillMaxSize()
    ) { paddingValues ->
        GoogleMapWrapper()
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(Constants.Spacing.small),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Constants.Spacing.small)
        ) {
            if (!LocalInspectionMode.current) {
                val mapViewModel = hiltViewModel<MapViewModel>()
                val bearing by mapViewModel
                    .bearing
                    .collectAsState()
                MapOverlayButton(
                    icon = R.drawable.navigation,
                    onClick = mapViewModel::resetBearing,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .rotate(degrees = -bearing)
                )
            }
            MapOverlayButton(
                icon = R.drawable.add_location_alt,
                onClick = {}
            )
            MapOverlayButton(
                icon = R.drawable.undo,
                onClick = {}
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewMapWrapper() {
    PreviewWrapper {
        ScaffoldWrapper {
            MapWrapper()
        }
    }
}
