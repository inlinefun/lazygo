package com.github.inlinefun.lazygo.data

import com.github.inlinefun.lazygo.R

internal object UserPreferences {
    val all = listOf(
        AmoledTheme,
        TravelMode,
        TrafficAwareness
    )
    object AmoledTheme: BooleanPreferenceKey(
        id = "amoled_theme",
        label = R.string.setting_amoled_mode,
        defaultValue = false
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