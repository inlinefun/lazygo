package com.github.inlinefun.lazygo.composables.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.composables.components.navigation.LazyTopBar
import com.github.inlinefun.lazygo.util.LazyGOTheme

@Composable
fun ActivitySettingsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            LazyTopBar(
                title = R.string.label_activity,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Preview
@Composable
private fun PreviewActivitySettingsScreen() {
    LazyGOTheme {
        ActivitySettingsScreen(
            onBack = { }
        )
    }
}
