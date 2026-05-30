package com.github.inlinefun.lazygo.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.github.inlinefun.lazygo.R

@Composable
fun DefaultTopbar(
    @StringRes
    title: Int,
    onAction: () -> Unit,
    @DrawableRes
    icon: Int? = R.drawable.arrow_back
) {
    TopAppBar(
        navigationIcon = {
            if (icon != null) {
                IconButton(
                    onClick = onAction
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null
                    )
                }
            }
        },
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        }
    )
}
