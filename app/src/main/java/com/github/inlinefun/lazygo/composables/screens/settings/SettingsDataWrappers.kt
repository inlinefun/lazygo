package com.github.inlinefun.lazygo.composables.screens.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.inlinefun.lazygo.data.preferences.PreferenceType

data class PreferenceCategory(
    @field:StringRes
    val title: Int,
    val items: List<PreferenceItem<*>>
)

data class PreferenceItem<T>(
    @field:StringRes
    val title: Int,
    @field:StringRes
    val detail: Int? = null,
    @field:DrawableRes
    val icon: Int,
    val preference: T
) where T : PreferenceType<*, *>
