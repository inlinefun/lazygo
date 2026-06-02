package com.github.inlinefun.lazygo.composables.root.map.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.preferences.AppTheme
import com.github.inlinefun.lazygo.preferences.MapTheme
import com.github.inlinefun.lazygo.preferences.Preferences.Appearance
import com.github.inlinefun.lazygo.preferences.getPreferenceAsState

@Composable
fun MapCrosshair() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val appTheme by context.getPreferenceAsState(
            key = Appearance.appTheme
        )
        val mapTheme by context.getPreferenceAsState(
            key = Appearance.mapTheme
        )
        val useInvertedCrosshair by context.getPreferenceAsState(
            key = Appearance.invertedCrosshair
        )
        val isSystemInDarkTheme = isSystemInDarkTheme()
        val crosshairColor by remember(
            appTheme,
            mapTheme,
            useInvertedCrosshair,
            isSystemInDarkTheme
        ) {
            derivedStateOf {
                val appInDarkTheme = when (appTheme) {
                    AppTheme.SYSTEM_DEFAULT -> isSystemInDarkTheme
                    AppTheme.DARK -> true
                    AppTheme.LIGHT -> false
                }
                val mapInDarkTheme = when (mapTheme) {
                    MapTheme.FOLLOW_APP -> appInDarkTheme
                    MapTheme.SYSTEM_DEFAULT -> isSystemInDarkTheme
                    MapTheme.DARK -> true
                    MapTheme.LIGHT -> false
                }
                return@derivedStateOf when (mapInDarkTheme) {
                    true -> Color.White
                    false -> Color.Black
                }
            }
        }
        val color by animateColorAsState(
            targetValue = if (useInvertedCrosshair) {
                Color.White
            } else {
                crosshairColor
            }
        )
        Canvas(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center)
        ) {
            val width = 5f
            drawLine(
                color = color,
                strokeWidth = width,
                start = Offset(x = 0f, y = this.center.y),
                end = Offset(x = this.size.width, y = this.center.y),
                blendMode = if (useInvertedCrosshair) BlendMode.Difference else BlendMode.SrcOver
            )
            drawLine(
                color = color,
                strokeWidth = width,
                start = Offset(x = this.center.x, y = 0f),
                end = Offset(x = this.center.x, y = this.size.height),
                blendMode = if (useInvertedCrosshair) BlendMode.Difference else BlendMode.SrcOver
            )
        }
    }
}
