package com.github.inlinefun.lazygo.data.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface LazyNavRoute : NavKey {
    @Serializable
    data object Content : LazyNavRoute
    @Serializable
    data object Settings : LazyNavRoute
}

sealed interface LazyContentChoice : NavKey {
    @Serializable
    data object Map : LazyContentChoice

    @Serializable
    data object Activity : LazyContentChoice
}