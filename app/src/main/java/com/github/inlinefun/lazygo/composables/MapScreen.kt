package com.github.inlinefun.lazygo.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.rounded.PauseCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.Route
import androidx.compose.material.icons.rounded.Settings
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
import androidx.compose.runtime.collectAsState
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
import com.github.inlinefun.lazygo.data.RouteStatus
import com.github.inlinefun.lazygo.ui.Constants
import com.github.inlinefun.lazygo.util.copy
import com.github.inlinefun.lazygo.util.to
import com.github.inlinefun.lazygo.viewmodels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MapScreen(
    viewModel: MapViewModel,
    navigateToSettingsScreen: () -> Unit
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
    val focusedPosition by viewModel.focusedPosition.collectAsState()
    val address by viewModel.address.collectAsState()
    val routeState by viewModel.routeStatus.collectAsState()
    val checkpoints = viewModel.checkpoints
    val points by viewModel.points.collectAsState()
    val fallbackRoute by viewModel.failed.collectAsState()
    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContent(
                status = routeState,
                address = address,
                onStart = viewModel::startRoute,
                onStop = viewModel::stopRoute,
                onPause = viewModel::pauseRoute,
                toSettingsScreen = navigateToSettingsScreen
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
            markers = checkpoints,
            onFocusedPositionChange = {
                viewModel.updateFocusedLocation(
                    position = it,
                    context = context
                )
            },
            points = points,
            fallbackRoute = fallbackRoute,
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
            },
            onAddPoint = {
                focusedPosition?.let {
                    viewModel.addCheckpoint(it)
                }
            },
            onRemovePoint = viewModel::removeLastCheckpoint,
            pointsExist = checkpoints.isNotEmpty()
        )
    }
}

@Composable
private fun BottomSheetContent(
    status: RouteStatus,
    address: String?,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onPause: () -> Unit,
    toSettingsScreen: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Constants.Spacing.medium),
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
                    text = address ?: stringResource(R.string.label_unknown_address),
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
            AnimatedVisibility(
                visible = status != RouteStatus.INACTIVE
            ) {
                OutlinedButton(
                    onClick = onStop,
                    modifier = Modifier
                        .weight(1.0f)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.StopCircle,
                        contentDescription = null
                    )
                    Spacer(
                        modifier = Modifier
                            .size(Constants.Spacing.small)
                    )
                    Text(
                        text = stringResource(R.string.label_stop),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Button(
                onClick = if (status == RouteStatus.ACTIVE) onPause else onStart,
                modifier = Modifier
                    .weight(1.0f)
            ) {
                AnimatedContent(
                    targetState = if (status == RouteStatus.ACTIVE) Icons.Rounded.PauseCircle else Icons.Rounded.PlayCircle
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
                    targetState = when(status) {
                        RouteStatus.ACTIVE -> R.string.label_pause
                        RouteStatus.INACTIVE -> R.string.label_start
                        RouteStatus.PAUSED -> R.string.label_continue
                    }
                ) { resourceId ->
                    Text(
                        text = stringResource(resourceId),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.medium),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = toSettingsScreen,
                modifier = Modifier
                    .weight(1.0f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier
                        .size(Constants.Spacing.small)
                )
                Text(
                    text = stringResource(R.string.label_settings),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun MapContent(
    state: CameraPositionState,
    onLoad: () -> Unit,
    markers: List<LatLng>,
    onFocusedPositionChange: (LatLng) -> Unit,
    points: List<LatLng>,
    fallbackRoute: Boolean,
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
                markers.forEach { location ->
                    val state = rememberUpdatedMarkerState(position = location)
                    Marker(
                        state = state
                    )
                }
                Polyline(
                    points = points,
                    pattern = listOf(
                        Dash(Constants.Sizing.large.value),
                        Gap(Constants.Spacing.medium.value)
                    ).takeIf { fallbackRoute },
                    color = MaterialTheme.colorScheme.error.takeIf {
                        fallbackRoute
                    } ?: MaterialTheme.colorScheme.primary
                )
            }
        }
        MapCrosshair(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}