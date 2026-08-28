package com.github.inlinefun.lazygo.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface LazyNavRoute : NavKey {
    @Serializable data object Base : LazyNavRoute
}

sealed interface LazyBaseRoute : NavKey {
    @Serializable data object Map : LazyBaseRoute
    @Serializable data object Activity : LazyBaseRoute
}
