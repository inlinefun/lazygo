package com.github.inlinefun.lazygo.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.ui.Constants
import com.github.inlinefun.lazygo.ui.PreviewTheme
import com.google.maps.android.compose.GoogleMap

@Composable
fun MapScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.Padding.small)
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
        ) {

        }
    }
}

@Preview
@Composable
fun PreviewMapScreen() {
    PreviewTheme {
        MapScreen()
    }
}
