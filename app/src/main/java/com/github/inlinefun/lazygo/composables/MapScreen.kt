package com.github.inlinefun.lazygo.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.ui.PreviewTheme

@Composable
fun MapScreen() {
    Text(text = "Map screen")
}

@Preview
@Composable
fun PreviewMapScreen() {
    PreviewTheme {
        MapScreen()
    }
}
