package com.github.inlinefun.lazygo.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material.icons.rounded.NearMe
import androidx.compose.material.icons.rounded.Route
import androidx.compose.material.icons.rounded.ZoomIn
import androidx.compose.material.icons.rounded.ZoomOut
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.ui.Constants
import com.github.inlinefun.lazygo.ui.PreviewTheme
import com.github.inlinefun.lazygo.util.copy
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MapScreen() {
    val scope = rememberCoroutineScope()
    val cameraState = rememberCameraPositionState {}
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContent()
        },
        sheetDragHandle = {
            BottomSheetDragHandle()
        },
        sheetPeekHeight = 160.dp,
        scaffoldState = scaffoldState,
        sheetSwipeEnabled = true,
        modifier = Modifier
            .fillMaxSize()
    ) { _ ->
        MapContent(
            state = cameraState
        )
        MapOverlay(
            mapDirection = cameraState.position.bearing,
            resetDirection = {
                scope.launch {
                    val update = CameraUpdateFactory.newCameraPosition(
                        cameraState.position.copy(
                            bearing = 0.0f
                        )
                    )
                    cameraState.animate(update, 200)
                }
            },
            zoomIn = {
                scope.launch {
                    val update = CameraUpdateFactory.zoomIn()
                    cameraState.animate(update, 200)
                }
            },
            zoomOut = {
                scope.launch {
                    val update = CameraUpdateFactory.zoomOut()
                    cameraState.animate(update, 200)
                }}
        )
    }
}

@Composable
private fun BottomSheetContent() {
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
                onClick = {},
                modifier = Modifier
                    .weight(1.0f)
            ) {
                Text(
                    text = stringResource(R.string.label_add_point),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .weight(1.0f)
            ) {
                Text(
                    text = stringResource(R.string.label_start),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun BottomSheetDragHandle() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .padding(Constants.Padding.small)
                .size(
                    width = 40.dp,
                    height = 4.dp
                ),
            content = {}
        )
    }
}

@Composable
private fun MapContent(
    state: CameraPositionState
) {
    GoogleMap(
        cameraPositionState = state,
        mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = false,
            compassEnabled = false,
        ),
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}

@Composable
private fun MapOverlay(
    mapDirection: Float,
    resetDirection: () -> Unit,
    zoomIn: () -> Unit,
    zoomOut: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(Constants.Spacing.medium),
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.Padding.small)
    ) {
        MapOverlayButton(
            onClick = resetDirection
        ) {
            Icon(
                imageVector = Icons.Rounded.NearMe,
                contentDescription = null,
                modifier = Modifier
                    .rotate(-45.0f - mapDirection) // normalize the rotation
            )
        }
        MapOverlayButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Rounded.MyLocation,
                contentDescription = null
            )
        }
        MapOverlayButton(
            onClick = zoomIn
        ) {
            Icon(
                imageVector = Icons.Rounded.ZoomIn,
                contentDescription = null
            )
        }
        MapOverlayButton(
            onClick = zoomOut
        ) {
            Icon(
                imageVector = Icons.Rounded.ZoomOut,
                contentDescription = null
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewMapScreen() {
    PreviewTheme {
        MapScreen()
    }
}
