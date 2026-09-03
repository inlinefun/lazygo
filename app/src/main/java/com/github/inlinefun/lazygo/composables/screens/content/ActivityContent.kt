package com.github.inlinefun.lazygo.composables.screens.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.util.LazyGOTheme

@Composable
fun ActivityContent() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.msg_feature_not_implemented)
        )
    }
}

@Preview
@Composable
private fun PreviewActivityContent() {
    LazyGOTheme {
        ActivityContent()
    }
}
