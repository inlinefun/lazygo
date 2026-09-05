package com.github.inlinefun.lazygo.composables.screens.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.composables.components.map.LazyMapContent
import com.github.inlinefun.lazygo.composables.components.map.LazyMapOverlay
import com.github.inlinefun.lazygo.util.LazyGOTheme
import com.github.inlinefun.lazygo.util.ScreenWrapper

@Composable
fun MapContent() {
    if (LocalInspectionMode.current) {
        ScreenWrapper {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Maps functionality is disabled in preview mode"
                )
            }
        }
    } else {
        LazyMapContent()
        LazyMapOverlay()
    }
}

@Preview
@Composable
private fun PreviewMapContent() {
    LazyGOTheme {
        MapContent()
    }
}
