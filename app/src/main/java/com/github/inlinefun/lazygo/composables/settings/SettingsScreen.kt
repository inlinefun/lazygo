package com.github.inlinefun.lazygo.composables.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.components.DefaultTopbar

@Composable
fun SettingsScreen(
    toPreviousScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopbar(
                title = R.string.label_settings,
                onAction = toPreviousScreen
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewSettingsScreen() {
    PreviewWrapper {
        SettingsScreen(
            toPreviousScreen = {}
        )
    }
}
