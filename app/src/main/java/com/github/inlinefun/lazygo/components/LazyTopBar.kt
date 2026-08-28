package com.github.inlinefun.lazygo.components

import androidx.annotation.StringRes
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
import com.github.inlinefun.lazygo.common.PreviewWrapper

@Composable
fun LazyTopBar(
    @StringRes title: Int,
    onBack: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = stringResource(id = R.string.label_back),
                modifier = Modifier
                    .padding(all = 4.dp)
                    .clickable(
                        enabled = true,
                        onClick = onBack
                    )
                    .padding(all = 12.dp)
            )
        },
        title = {
            Text(
                text = stringResource(id = title)
            )
        },
    )
}

@Preview
@Composable
private fun PreviewLazyTopBar() {
    PreviewWrapper {
        LazyTopBar(
            title = R.string.label_settings,
            onBack = {  }
        )
    }
}
