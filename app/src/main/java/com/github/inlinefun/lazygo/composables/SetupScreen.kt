package com.github.inlinefun.lazygo.composables

import android.Manifest
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.ShareLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.data.PermissionStep
import com.github.inlinefun.lazygo.ui.Constants
import com.github.inlinefun.lazygo.viewmodels.AppViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun SetupScreen(
    viewModel: AppViewModel,
    navigateToMapScreen: () -> Unit
) {
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    val locationProviderState by viewModel.isLocationProvider.collectAsState()
    val pages = arrayOf(
        PermissionStep(
            titleId = R.string.title_grant_location_permission,
            descriptionId = R.string.description_grant_location_permission,
            icon = Icons.Rounded.LocationOn,
            buttonLabelId = R.string.label_grant_location_permission,
            action = {
                locationPermissionState.launchPermissionRequest()
            },
            buttonState = {
                locationPermissionState.status == PermissionStatus.Granted
            }
        ),
        PermissionStep(
            titleId = R.string.title_select_location_provider,
            descriptionId = R.string.description_select_location_provider,
            icon = Icons.Rounded.ShareLocation,
            buttonLabelId = R.string.label_select_location_provider,
            action = AppViewModel::setLocationProvider,
            buttonState = {
                locationProviderState
            }
        ),
    )
    val scope = rememberCoroutineScope()
    val pager = rememberPagerState(
        pageCount = pages::size
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, _ ->
            viewModel.refreshLocationProviderStatus()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.Padding.medium)
    ) {
        val index = pager.currentPage
        val page = pages[index]
        HorizontalPager(
            state = pager,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Constants.Spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1.0f)
                )
                Icon(
                    imageVector = page.icon,
                    contentDescription = stringResource(page.titleId),
                    modifier = Modifier
                        .size(Constants.Sizing.large)
                )
                Text(
                    text = stringResource(page.titleId),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = stringResource(page.descriptionId),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .weight(1.0f)
                )
            }
        }
        Button(
            onClick = {
                scope.launch {
                    val pageAction = suspend {
                        if (pager.canScrollForward) {
                            pager.animateScrollToPage(
                                page = index + 1
                            )
                        } else {
                            navigateToMapScreen()
                        }
                    }
                    if (page.buttonState(viewModel)) {
                        pageAction()
                    } else {
                        page.action(viewModel)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = when {
                    !page.buttonState(viewModel) -> page.buttonLabelId
                    pager.canScrollForward -> R.string.label_next
                    else -> R.string.label_get_started
                }
            ) { resourceId ->
                Text(
                    text = stringResource(resourceId),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
