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
            enumEntries = AppTheme.entries
        )
        val amoledTheme = PreferenceKey.Switch(
            id = "appearance.app.amoled_theme",
            label = R.string.preference_label_amoled_theme,
            description = R.string.preference_description_amoled_theme,
            icon = R.drawable.dark_mode,
            defaultValue = true
        )
        val mapTheme = PreferenceKey.Choice(
            id = "appearance.map.theme",
            label = R.string.preference_label_map_theme,
            icon = R.drawable.palette,
            defaultValue = MapTheme.FOLLOW_APP,
            enumEntries = MapTheme.entries
        )
        val mapType = PreferenceKey.Choice(
            id = "appearance.map.type",
            label = R.string.preference_label_map_type,
            icon = R.drawable.map,
            defaultValue = MapType.DEFAULT,
            enumEntries = MapType.entries
        )
        val invertedCrosshair = PreferenceKey.Switch(
            id = "appearance.map.invert_crosshair",
            label = R.string.preference_label_inverted_crosshair,
            description = R.string.preference_description_inverted_crosshair,
            icon = R.drawable.point_scan,
            defaultValue = false
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
                preferences = listOf(mapTheme, mapType, invertedCrosshair)
            )
        )
    }

}