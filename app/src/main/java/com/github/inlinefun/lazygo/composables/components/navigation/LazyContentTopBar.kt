package com.github.inlinefun.lazygo.composables.components.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.util.LazyGOTheme

@Composable
fun LazyContentTopBar(
    navigateToSettings: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = stringResource(id = R.string.label_settings),
                modifier = Modifier
                    .padding(all = 4.dp)
                    .clickable(
                        enabled = true,
                        onClick = navigateToSettings,
                        onClickLabel = stringResource(id = R.string.label_settings)
                    )
                    .padding(all = 12.dp)
            )
        }
    )
}

@Preview
@Composable
private fun PreviewLazyContentTopBar() {
    LazyGOTheme {
        LazyContentTopBar(
            navigateToSettings = { }
        )
    }
}
