package com.github.inlinefun.lazygo.preferences

import androidx.annotation.StringRes
import com.github.inlinefun.lazygo.R

enum class AppTheme(
    @field:StringRes
    override val label: Int,
    override val value: String
) : PreferenceEnum {
    SYSTEM_DEFAULT(
        label = R.string.value_system_default,
        value = "system_default"
    ),
    DARK(
        label = R.string.value_dark,
        value = "dark"
    ),
    LIGHT(
        label = R.string.value_light,
        value = "light"
    );
}

enum class MapTheme(
    @field:StringRes
    override val label: Int,
    override val value: String
) : PreferenceEnum {
    FOLLOW_APP(
        label = R.string.value_follow_app,
        value = "follow_app"
    ),
    SYSTEM_DEFAULT(
        label = R.string.value_system_default,
        value = "system_default"
    ),
    DARK(
        label = R.string.value_dark,
        value = "dark"
    ),
    LIGHT(
        label = R.string.value_light,
        value = "light"
    );
}

enum class MapType(
    @field:StringRes
    override val label: Int,
    override val value: String
) : PreferenceEnum {
    DEFAULT(
        label = R.string.value_default,
        value = "default"
    ),
    TERRAIN(
        label = R.string.value_terrain,
        value = "terrain"
    );
}
