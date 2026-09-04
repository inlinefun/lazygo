package com.github.inlinefun.lazygo.composables.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.composables.components.navigation.LazyTopBar
import com.github.inlinefun.lazygo.util.Constants
import com.github.inlinefun.lazygo.util.LazyGOTheme

@Composable
fun RoutesSettingsScreen(
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            LazyTopBar(
                title = R.string.label_routes,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(all = Constants.Spacing.small)
                .verticalScroll(scrollState)
        ) {

        }
    }
}

@Preview
@Composable
private fun PreviewRoutesSettingsScreen() {
    LazyGOTheme {
        RoutesSettingsScreen(
            onBack = { }
        )
    }
}
