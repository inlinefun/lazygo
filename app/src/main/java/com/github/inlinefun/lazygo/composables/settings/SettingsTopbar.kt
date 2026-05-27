package com.github.inlinefun.lazygo.composables.settings

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.PreviewWrapper

@Composable
fun SettingsTopbar(
    toPreviousScreen: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = toPreviousScreen
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.label_settings)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    )
}

@Preview
@Composable
private fun PreviewSettingsTopbar() {
    PreviewWrapper {
        SettingsTopbar(
            toPreviousScreen = {}
        )
    }
}
