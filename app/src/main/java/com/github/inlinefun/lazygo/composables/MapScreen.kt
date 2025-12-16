package com.github.inlinefun.lazygo.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddLocationAlt
import androidx.compose.material.icons.rounded.PauseCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.Route
import androidx.compose.material.icons.rounded.StopCircle
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.ui.Constants
import com.github.inlinefun.lazygo.util.copy
import com.github.inlinefun.lazygo.util.to
import com.github.inlinefun.lazygo.viewmodels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MapScreen(
    viewModel: MapViewModel
) {
    val sheetPeekHeight = 160.dp
    val sheetHandleHeight = 4.dp
    val sheetHandlePadding = Constants.Padding.small
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val cameraState = rememberCameraPositionState {}
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    var started by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf<LatLng?>(null) }
    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContent(
                started = started,
                onStart = {
                    started = true
                },
                onStop = {
                    started = false
                },
                onPause = {
                    started = false
                },
                onAddPoint = {}
            )
        },
        sheetDragHandle = {
            BottomSheetDragHandle(
                height = sheetHandleHeight,
                padding = sheetHandlePadding
            )
        },
        sheetPeekHeight = sheetPeekHeight,
        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        modifier = Modifier
            .fillMaxSize()
    ) { _ ->
        MapContent(
            state = cameraState,
            onLoad = {
                viewModel.getLocation(
                    context = context,
                    onSuccess = { location ->
                        scope.launch {
                            cameraState.to(location)
                        }
                    },
                    onError = {}
                )
            },
            onFocusedPositionChange = {
                location = it
            },
            modifier = Modifier
                .padding(
                    // height - (handle height, padding on top + bottom)
                    bottom = sheetPeekHeight - (sheetHandleHeight + (sheetHandlePadding * 2))
                )
        )
        MapOverlay(
            mapDirection = cameraState.position.bearing,
            onMapReorient = {
                scope.launch {
                    val update = CameraUpdateFactory.newCameraPosition(
                        cameraState.position.copy(
                            bearing = 0.0f
                        )
                    )
                    cameraState.animate(update, 200)
                }
            },
            onNearestLocation = {
                scope.launch {
                    viewModel.getLocation(
                        context = context,
                        onSuccess = { location ->
                            scope.launch {
                                cameraState.to(location)
                            }
                        },
                        onError = {}
                    )
                }
            },
            onZoomIn = {
                scope.launch {
                    val update = CameraUpdateFactory.zoomIn()
                    cameraState.animate(update, 200)
                }
            },
            onZoomOut = {
                scope.launch {
                    val update = CameraUpdateFactory.zoomOut()
                    cameraState.animate(update, 200)
                }
            }
        )
    }
}

@Composable
private fun BottomSheetContent(
    started: Boolean,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onPause: () -> Unit,
    onAddPoint: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Constants.Padding.medium,
                vertical = Constants.Padding.small
            )
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            ),
            contentPadding = ButtonDefaults.TextButtonContentPadding,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Constants.Padding.small)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Route,
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.label_unknown_address),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    softWrap = false
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.medium),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = if (started) onStop else onAddPoint,
                modifier = Modifier
                    .weight(1.0f)
            ) {
                AnimatedContent(
                    targetState = if (started) Icons.Rounded.StopCircle else Icons.Rounded.AddLocationAlt
                ) { target ->
                    Icon(
                        imageVector = target,
                        contentDescription = null
                    )
                }
                Spacer(
                    modifier = Modifier
                        .size(Constants.Spacing.small)
                )
                AnimatedContent(
                    targetState = if (started) R.string.label_stop else R.string.label_add_point
                ) { resourceId ->
                    Text(
                        text = stringResource(resourceId),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Button(
                onClick = if (started) onPause else onStart,
                modifier = Modifier
                    .weight(1.0f)
            ) {
                AnimatedContent(
                    targetState = if (started) Icons.Rounded.PauseCircle else Icons.Rounded.PlayCircle
                ) { target ->
                    Icon(
                        imageVector = target,
                        contentDescription = null
                    )
                }
                Spacer(
                    modifier = Modifier
                        .size(Constants.Spacing.small)
                )
                AnimatedContent(
                    targetState = if (started) R.string.label_pause else R.string.label_start
                ) { resourceId ->
                    Text(
                        text = stringResource(resourceId),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun MapContent(
    state: CameraPositionState,
    onLoad: () -> Unit,
    onFocusedPositionChange: (LatLng) -> Unit,
    modifier: Modifier = Modifier
) {
    var frameDelayed by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        withFrameNanos { }
        frameDelayed = true
        delay(500L)
        onLoad()
    }
    LaunchedEffect(state) {
        snapshotFlow { state.isMoving }
            .filter { moving -> !moving }
            .collect {
                onFocusedPositionChange(state.position.target)
            }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (frameDelayed) {
            GoogleMap(
                cameraPositionState = state,
                mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
                properties = MapProperties(
                    isMyLocationEnabled = true
                ),
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = false,
                    compassEnabled = false,
                    zoomControlsEnabled = false,
                    indoorLevelPickerEnabled = false,
                    mapToolbarEnabled = false
                ),
                modifier = Modifier
                    .fillMaxSize()
            ) {
            }
        }
        MapCrosshair(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}