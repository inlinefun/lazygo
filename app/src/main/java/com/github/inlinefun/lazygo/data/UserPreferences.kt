package com.github.inlinefun.lazygo.data

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.inlinefun.lazygo.R

internal object UserPreferences {
    val all = listOf(
        PreferenceCategory(
            label = R.string.setting_category_ui,
            preferences = listOf(
                AppTheme,
                AmoledTheme,
                MapTheme
            )
        ),
        PreferenceCategory(
            label = R.string.setting_category_routing,
            preferences = listOf(
                TrafficAwareness,
                TravelMode
            )
        ),
        PreferenceCategory(
            label = R.string.setting_category_spoofing,
            preferences = listOf(
                TravelSpeed
            )
        )
    )
    object AmoledTheme: BooleanPreferenceKey(
        id = "amoled_theme",
        label = R.string.setting_amoled_mode,
        defaultValue = false,
        visibility = { store ->
            val theme by store
                .flow(AppTheme)
                .collectAsState(
                    initial = AppTheme.defaultValue
                )
            theme == UITheme.DARK || (theme == UITheme.AUTO && isSystemInDarkTheme())
        }
    )
    object AppTheme: EnumPreferenceKey<UITheme>(
        id = "app_theme",
        label = R.string.setting_app_theme,
        defaultValue = UITheme.AUTO,
        serializer = PreferenceSerializer.EnumSerializer(
            values = UITheme.entries.toTypedArray()
        )
    )
    object MapTheme: EnumPreferenceKey<UITheme>(
        id = "map_theme",
        label = R.string.setting_map_theme,
        defaultValue = UITheme.AUTO,
        serializer = PreferenceSerializer.EnumSerializer(
            values = UITheme.entries.toTypedArray()
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
    object TravelSpeed: IntPreferenceKey(
        id = "travel_speed",
        label = R.string.setting_travel_speed,
        defaultValue = 6,
        minimum = 2,
        maximum = 60,
        suffix = "km/h"
    )
}