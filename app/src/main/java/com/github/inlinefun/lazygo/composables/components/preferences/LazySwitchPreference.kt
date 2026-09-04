package com.github.inlinefun.lazygo.composables.components.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.composables.components.common.LazySwitch
import com.github.inlinefun.lazygo.data.preferences.LazyPreferences
import com.github.inlinefun.lazygo.data.preferences.PreferenceType
import com.github.inlinefun.lazygo.data.preferences.getPreferenceAsState
import com.github.inlinefun.lazygo.data.preferences.setPreference
import com.github.inlinefun.lazygo.util.LazyGOTheme
import kotlinx.coroutines.launch

@Composable
fun LazySwitchPreference(
    @StringRes
    title: Int,
    @StringRes
    detail: Int? = null,
    @DrawableRes
    icon: Int,
    preference: PreferenceType.Switch,
    count: Int = 1,
    index: Int = 0
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val value by context.getPreferenceAsState(preference)
    LazyPreferenceItem(
        title = title,
        detail = detail,
        icon = icon,
        index = index,
        count = count,
        onClick = {
            scope.launch {
                context.setPreference(preference, !value)
            }
        },
        trailingContent = {
            LazySwitch(
                checked = value,
                onToggle = { value ->
                    scope.launch {
                        context.setPreference(preference, value)
                    }
                }
            )
        }
    )
}

@Preview
@Composable
private fun PreviewLazySwitchPreference() {
    LazyGOTheme {
        LazySwitchPreference(
            title = R.string.pref_label_amoled_theme,
            detail = R.string.pref_detail_amoled_theme,
            icon = R.drawable.dark_mode,
            preference = LazyPreferences.Appearance.amoledTheme
        )
    }
}