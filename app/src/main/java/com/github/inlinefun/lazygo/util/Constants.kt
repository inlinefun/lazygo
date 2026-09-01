@file:Suppress("unused")

package com.github.inlinefun.lazygo.util

import androidx.compose.ui.unit.dp

sealed interface Constants {
    data object Spacing {
        val extraSmall = 4.dp
        val small = 8.dp
        val medium = 16.dp
        val large = 24.dp
        val extraLarge = 32.dp
    }
}