package com.github.inlinefun.lazygo.ui

import androidx.compose.ui.unit.dp

sealed interface Constants {
    object Padding {
        val small = 12.dp
        val medium = 24.dp
    }
    object Spacing {
        val medium = 12.dp
    }
    object Sizing {
        val large = 96.dp
    }
}
