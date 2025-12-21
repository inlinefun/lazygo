package com.github.inlinefun.lazygo.data

import com.github.inlinefun.lazygo.R

internal object UserPreferences {
    val all = listOf(
        PreferenceCategory(
            label = R.string.setting_category_ui,
            preferences = listOf(
                AppTheme,
                AmoledTheme
            )
        ),
        PreferenceCategory(
            label = R.string.setting_category_routing,
            preferences = listOf(
                TrafficAwareness,
                TravelMode
            )
        )
    )
    object AmoledTheme: BooleanPreferenceKey(
        id = "amoled_theme",
        label = R.string.setting_amoled_mode,
        defaultValue = false
    )
    object AppTheme: EnumPreferenceKey<AppThemes>(
        id = "app_theme",
        label = R.string.setting_app_theme,
        defaultValue = AppThemes.AUTO,
        serializer = PreferenceSerializer.EnumSerializer(
            values = AppThemes.entries.toTypedArray()
        )
    )
    object TrafficAwareness: BooleanPreferenceKey(
        id = "traffic_awareness",
        label = R.string.setting_traffic_awareness,
        defaultValue = false
    )
    object TravelMode: EnumPreferenceKey<TravelModes>(
        id = "travel_mode",
        label = R.string.setting_travel_mode,
        defaultValue = TravelModes.WALK,
        serializer = PreferenceSerializer.EnumSerializer(
            values = TravelModes.entries.toTypedArray()
        )
    )
}