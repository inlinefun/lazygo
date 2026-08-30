package com.github.inlinefun.lazygo.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface LazyNavRoute : NavKey {
    @Serializable data object Root : LazyNavRoute
    @Serializable data object Settings : LazyNavRoute {
        @Serializable data object AppearanceSettings : LazyNavRoute
    }
}

sealed interface LazyBaseRoute : NavKey {
    @Serializable data object Map : LazyBaseRoute
    @Serializable data object Activity : LazyBaseRoute
}
