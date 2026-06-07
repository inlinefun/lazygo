package com.github.inlinefun.lazygo.composables.root

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.viewmodel.PermissionsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
fun RowScope.PermissionActions(
    permissions: PermissionsViewModel = hiltViewModel()
) {
    val locationPermission = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    val isMockLocationProvider by permissions
        .isMockLocationProvider
        .collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, _ ->
            permissions.refreshMockLocationPermission()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    this.run {
        AnimatedVisibility(
            visible = locationPermission.status != PermissionStatus.Granted
        ) {
            IconButton(
                onClick = {
                    locationPermission.launchPermissionRequest()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.location_off),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
        AnimatedVisibility(
            visible = !isMockLocationProvider
        ) {
            IconButton(
                onClick = {
                    try {
                        permissions.startActivityToSelectMockLocationProvider()
                    } catch (_: Exception) {
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_location_alt),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
