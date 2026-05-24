package com.github.inlinefun.lazygo.common

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface NavRoute : NavKey {
    @Serializable
    data object Root : NavRoute
}

sealed interface SubRoute : NavKey {
    @Serializable
    data object Map : SubRoute
    @Serializable
    data object Activity : SubRoute
}
