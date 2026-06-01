package com.github.inlinefun.lazygo.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.inlinefun.lazygo.R

sealed interface Preferences {
    val id: String

    @get:StringRes
    val label: Int

    @get:DrawableRes
    val icon: Int
    val categories: List<PreferenceCategory>

    data object Appearance : Preferences {
        val appTheme = PreferenceKey.Choice(
            id = "appearance.app.theme",
            label = R.string.preference_label_app_theme,
            icon = R.drawable.palette,
            defaultValue = AppTheme.SYSTEM_DEFAULT,
            enumClass = AppTheme::class.java,
            enumEntries = AppTheme.entries
        )
        val amoledTheme = PreferenceKey.Switch(
            id = "appearance.app.amoled_theme",
            label = R.string.preference_label_amoled_theme,
            description = R.string.preference_description_amoled_theme,
            icon = R.drawable.dark_mode,
            defaultValue = false
        )
        val mapTheme = PreferenceKey.Choice(
            id = "appearance.map.theme",
            label = R.string.preference_label_map_theme,
            icon = R.drawable.palette,
            defaultValue = MapTheme.FOLLOW_APP,
            enumClass = MapTheme::class.java,
            enumEntries = MapTheme.entries
        )
        val mapType = PreferenceKey.Choice(
            id = "appearance.map.type",
            label = R.string.preference_label_map_type,
            icon = R.drawable.map,
            defaultValue = MapType.DEFAULT,
            enumClass = MapType::class.java,
            enumEntries = MapType.entries
        )
        override val id: String = "APPEARANCE"

        @field:StringRes
        override val label: Int = R.string.label_appearance

        @field:DrawableRes
        override val icon: Int = R.drawable.palette
        override val categories = listOf(
            PreferenceCategory(
                label = R.string.label_app,
                preferences = listOf(appTheme, amoledTheme)
            ),
            PreferenceCategory(
                label = R.string.label_map,
                preferences = listOf(mapTheme, mapType)
            )
        )
    }

}