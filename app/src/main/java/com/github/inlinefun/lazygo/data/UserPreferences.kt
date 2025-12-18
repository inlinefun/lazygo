package com.github.inlinefun.lazygo.data

sealed interface UserPreferences {
    object TravelMode: EnumPreferenceKey<TravelModes>(
        id = "travel_mode",
        defaultValue = TravelModes.WALK,
        serializer = PreferenceSerializer.EnumSerializer(
            values = TravelModes.entries.toTypedArray()
        )
    )
}