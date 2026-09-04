package com.github.inlinefun.lazygo.data.preferences

import androidx.annotation.StringRes
import com.github.inlinefun.lazygo.R

enum class PreferenceAppTheme(
    override val key: String,
    @field:StringRes
    override val label: Int
) : PreferenceEnum {
    SYSTEM_DEFAULT(
        key = "system_default",
        label = R.string.label_system_default
    ),
    DARK(
        key = "dark",
        label = R.string.label_dark
    ),
    LIGHT(
        key = "light",
        label = R.string.label_light
    );
}

enum class PreferenceMapTheme(
    override val key: String,
    @field:StringRes
    override val label: Int
) : PreferenceEnum {
    SYSTEM_DEFAULT(
        key = "system_default",
        label = R.string.label_system_default,
    ),
    LIGHT(
        key = "light",
        label = R.string.label_light
    ),
    DARK(
        key = "dark",
        label = R.string.label_dark
    );
}

enum class PreferenceMapType(
    override val key: String,
    @field:StringRes
    override val label: Int
) : PreferenceEnum {
    DEFAULT(
        key = "default",
        label = R.string.label_default
    ),
    SATELLITE(
        key = "satellite",
        label = R.string.label_satellite
    )
}
