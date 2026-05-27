package com.github.inlinefun.lazygo.composables.root

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.NavRoute
import kotlinx.coroutines.delay

@Composable
fun TopBar(
    navigateTo: (NavRoute) -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        var progress by remember { mutableFloatStateOf(0.0f) }
        val value by animateFloatAsState(
            targetValue = progress,
            visibilityThreshold = 0.005f
        )
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name)
                )
            },
            actions = {
                TopBarMenu(navigateTo)
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        )
        LaunchedEffect(Unit) {
            delay(1000L)
            progress = 25.000f
        }
        LinearProgressIndicator(
            progress = {
                value / 100f
            },
            gapSize = 3.dp,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}