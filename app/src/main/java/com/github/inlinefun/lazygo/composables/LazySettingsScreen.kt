package com.github.inlinefun.lazygo.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.components.LazyTopBar

@Composable
fun LazySettingsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            LazyTopBar(
                title = R.string.label_settings,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.label_settings)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLazySettingsScreen() {
    PreviewWrapper {
        LazySettingsScreen(
            onBack = {  }
        )
    }
}