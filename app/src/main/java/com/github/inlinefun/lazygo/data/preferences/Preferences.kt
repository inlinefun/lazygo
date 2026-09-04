package com.github.inlinefun.lazygo.data.preferences

sealed interface LazyPreferences {
    data object Appearance : LazyPreferences {
        val appTheme = PreferenceType.Choice(
            key = "appearance.app.theme",
            defaultValue = PreferenceAppTheme.SYSTEM_DEFAULT,
            entries = PreferenceAppTheme.entries
        )
        val amoledTheme = PreferenceType.Switch(
            key = "appearance.app.amoled",
            defaultValue = false
        )
        val mapTheme = PreferenceType.Choice(
            key = "appearance.map.theme",
            defaultValue = PreferenceMapTheme.SYSTEM_DEFAULT,
            entries = PreferenceMapTheme.entries
        )
        val mapType = PreferenceType.Choice(
            key = "apearance.map.type",
            defaultValue = PreferenceMapType.DEFAULT,
            entries = PreferenceMapType.entries
        )
    }
}
