package com.github.inlinefun.lazygo.preferences

sealed interface Preferences {
    data object Appearance {
        val appTheme = PreferenceKey.Choice(
            id = "appearance.app.theme",
            defaultValue = AppTheme.SYSTEM_DEFAULT,
            entries = AppTheme.entries
        )
        val amoledTheme = PreferenceKey.Switch(
            id = "appearance.app.amoled_theme",
            defaultValue = false
        )
    }
}
