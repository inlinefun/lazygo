package com.github.inlinefun.lazygo.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddLocationAlt
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material.icons.rounded.NearMe
import androidx.compose.material.icons.rounded.WrongLocation
import androidx.compose.material.icons.rounded.ZoomIn
import androidx.compose.material.icons.rounded.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.ui.Constants

@Composable
internal fun BottomSheetDragHandle(
    padding: Dp,
    height: Dp
) {
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
                .padding(padding)
                .size(
                    width = 40.dp,
                    height = height
                ),
            content = {}
        )
    }
}

@Composable
internal fun MapOverlayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        contentPadding = PaddingValues(),
        onClick = onClick,
        modifier = modifier
            .size(48.dp),
        content = content
    )
}

@Composable
internal fun MapOverlay(
    mapDirection: Float,
    onMapReorient: () -> Unit,
    onNearestLocation: () -> Unit,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onAddPoint: () -> Unit,
    onRemovePoint: () -> Unit,
    pointsExist: Boolean
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(Constants.Spacing.medium),
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.Padding.small)
    ) {
        MapOverlayButton(
            onClick = onMapReorient
        ) {
            Icon(
                imageVector = Icons.Rounded.NearMe,
                contentDescription = null,
                modifier = Modifier
                    .rotate(-45.0f - mapDirection) // normalize the rotation
            )
        }
        MapOverlayButton(
            onClick = onNearestLocation
        ) {
            Icon(
                imageVector = Icons.Rounded.MyLocation,
                contentDescription = null
            )
        }
        MapOverlayButton(
            onClick = onZoomIn
        ) {
            Icon(
                imageVector = Icons.Rounded.ZoomIn,
                contentDescription = null
            )
        }
        MapOverlayButton(
            onClick = onZoomOut
        ) {
            Icon(
                imageVector = Icons.Rounded.ZoomOut,
                contentDescription = null
            )
        }
        MapOverlayButton(
            onClick = onAddPoint
        ) {
            Icon(
                imageVector = Icons.Rounded.AddLocationAlt,
                contentDescription = null
            )
        }
        AnimatedVisibility(
            visible = pointsExist
        ) {
            MapOverlayButton(
                onClick = onRemovePoint
            ) {
                Icon(
                    imageVector = Icons.Rounded.WrongLocation,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
internal fun MapCrosshair(
    modifier: Modifier = Modifier
) {
    val color = MaterialTheme.colorScheme.onSurface
    Canvas(
        modifier = modifier
            .size(32.dp)
    ) {
        val stroke = 5f
        val center = size.width / 2
        drawLine(
            color = color,
            start = Offset(
                x = center,
                y = 0f
            ),
            end = Offset(
                x = center,
                y = size.height
            ),
            strokeWidth = stroke
        )
        drawLine(
            color = color,
            start = Offset(
                x = 0f,
                y = center
            ),
            end = Offset(
                x = size.width,
                y = center
            ),
            strokeWidth = stroke
        )
    }
}
