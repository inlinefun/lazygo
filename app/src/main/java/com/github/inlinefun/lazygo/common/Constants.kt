package com.github.inlinefun.lazygo.common

import androidx.compose.ui.unit.dp

@Suppress("unused")
sealed interface Constants {
    object Spacing {
        val extraSmall = 4.dp
        val small = 8.dp
        val medium = 16.dp
        val large = 24.dp
        val extraLarge = 32.dp
    }
    object IconSizing {
        val small = 16.dp
        val medium = 24.dp
        val large = 32.dp
        val extraLarge = 48.dp
    }
}
