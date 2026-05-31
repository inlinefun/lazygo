package com.github.inlinefun.lazygo.common

import androidx.navigation3.runtime.NavKey
import com.github.inlinefun.lazygo.preferences.Preferences
import kotlinx.serialization.Serializable

sealed interface NavRoute : NavKey {
    @Serializable
    data object Root : NavRoute

    @Serializable
    data object Settings : NavRoute
}

sealed interface SubRoute : NavKey {
    @Serializable
    data object Map : SubRoute

    @Serializable
    data object Activity : SubRoute
}

sealed interface SettingsRoute : NavKey {
    @Serializable
    data object SettingsList : SettingsRoute

    @Serializable
    data class SettingsDetails(
        val preferences: Preferences
    ) : SettingsRoute
}
