package com.github.inlinefun.lazygo.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface LazyNavRoute : NavKey {
    @Serializable data object Base : LazyNavRoute
}
