package com.github.inlinefun.lazygo.preferences

import androidx.annotation.StringRes
import com.github.inlinefun.lazygo.R

enum class AppTheme(
    @field:StringRes
    override val label: Int,
    override val value: String
) : PreferenceEnum {
    SYSTEM_DEFAULT(
        label = R.string.pref_val_system_default,
        value = "system_default"
    ),
    DARK(
        label = R.string.pref_val_dark,
        value = "dark"
    ),
    LIGHT(
        label = R.string.pref_val_light,
        value = "light"
    );
}
