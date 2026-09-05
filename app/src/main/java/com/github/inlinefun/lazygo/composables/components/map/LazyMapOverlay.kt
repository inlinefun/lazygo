package com.github.inlinefun.lazygo.composables.components.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.util.Constants
import com.github.inlinefun.lazygo.util.LazyGOTheme
import com.github.inlinefun.lazygo.util.ScreenWrapper

@Composable
fun LazyMapOverlay() {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = Constants.Spacing.small
        ),
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Constants.Spacing.small)
    ) {
        repeat(times = 4) {
            Button(
                onClick = { },
                colors = ButtonDefaults.elevatedButtonColors(),
                contentPadding = PaddingValues(
                    all = Constants.Spacing.extraSmall
                ),
                modifier = Modifier
                    .size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.map),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLazyMapOverlay() {
    LazyGOTheme {
        ScreenWrapper {
            LazyMapOverlay()
        }
    }
}