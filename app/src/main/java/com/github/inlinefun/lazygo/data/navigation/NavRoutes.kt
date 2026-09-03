package com.github.inlinefun.lazygo.data.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface LazySettingRoute : NavKey

sealed interface LazyNavRoute : NavKey {
    @Serializable
    data object Content : LazyNavRoute

    @Serializable
    data object Settings : LazyNavRoute {
        @Serializable
        data object Appearance : LazyNavRoute, LazySettingRoute

        @Serializable
        data object Routes : LazyNavRoute, LazySettingRoute

        @Serializable
        data object Activity : LazyNavRoute, LazySettingRoute
    }
}

sealed interface LazyContentChoice : NavKey {
    @Serializable
    data object Map : LazyContentChoice

    @Serializable
    data object Activity : LazyContentChoice
}