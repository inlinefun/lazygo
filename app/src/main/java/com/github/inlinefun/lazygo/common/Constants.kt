package com.github.inlinefun.lazygo.common

import androidx.compose.ui.unit.dp

@Suppress("unused")
sealed interface Constants {
    data object Spacing {
        val extraSmall = 4.dp
        val small = 8.dp
        val medium = 16.dp
        val large = 24.dp
        val extraLarge = 32.dp
    }
}
