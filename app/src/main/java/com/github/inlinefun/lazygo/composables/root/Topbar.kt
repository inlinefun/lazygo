package com.github.inlinefun.lazygo.composables.root

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.NavRoute
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainTopbar(
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
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
            },
            actions = {
                if (!LocalInspectionMode.current) {
                    this.PermissionActions()
                }
                TopBarMenu(navigateTo)
            }
        )
        LaunchedEffect(Unit) {
            delay(1000L.milliseconds)
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