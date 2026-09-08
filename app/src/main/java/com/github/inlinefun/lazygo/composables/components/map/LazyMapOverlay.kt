package com.github.inlinefun.lazygo.composables.components.map

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.data.preferences.LazyPreferences
import com.github.inlinefun.lazygo.data.preferences.PreferenceAppTheme
import com.github.inlinefun.lazygo.data.preferences.PreferenceMapTheme
import com.github.inlinefun.lazygo.data.preferences.getPreferenceAsState
import com.github.inlinefun.lazygo.util.Constants
import com.github.inlinefun.lazygo.util.LazyGOTheme
import com.github.inlinefun.lazygo.util.ScreenWrapper

@Composable
fun LazyMapOverlay() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        MapOverlayCrosshair()
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = Constants.Spacing.small
        ),
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Constants.Spacing.small)
    ) {
        MapOverlayButton(
            icon = R.drawable.add_location_alt,
            action = { }
        )
        MapOverlayButton(
            icon = R.drawable.undo,
            action = { }
        )
    }
}

@Composable
private fun MapOverlayCrosshair() {
    val context = LocalContext.current
    val appTheme by context.getPreferenceAsState(
        preference = LazyPreferences.Appearance.appTheme
    )
    val mapTheme by context.getPreferenceAsState(
        preference = LazyPreferences.Appearance.mapTheme
    )
    val useInvertedCrosshair by context.getPreferenceAsState(
        preference = LazyPreferences.Appearance.invertedCrosshair
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
                PreferenceAppTheme.SYSTEM_DEFAULT -> isSystemInDarkTheme
                PreferenceAppTheme.DARK -> true
                PreferenceAppTheme.LIGHT -> false
            }
            val mapInDarkTheme = when (mapTheme) {
                PreferenceMapTheme.FOLLOW_APP -> appInDarkTheme
                PreferenceMapTheme.SYSTEM_DEFAULT -> isSystemInDarkTheme
                PreferenceMapTheme.DARK -> true
                PreferenceMapTheme.LIGHT -> false
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

@Composable
private fun MapOverlayButton(
    @DrawableRes
    icon: Int,
    action: () -> Unit
) {
    Button(
        onClick = action,
        colors = ButtonDefaults.elevatedButtonColors(),
        contentPadding = PaddingValues(
            all = Constants.Spacing.extraSmall
        ),
        modifier = Modifier
            .size(48.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun PreviewLazyMapOverlay() {
    LazyGOTheme {
        ScreenWrapper {
            LazyMapOverlay()
        }
    }
}