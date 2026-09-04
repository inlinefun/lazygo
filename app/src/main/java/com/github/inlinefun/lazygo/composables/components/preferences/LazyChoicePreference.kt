package com.github.inlinefun.lazygo.composables.components.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.data.preferences.LazyPreferences
import com.github.inlinefun.lazygo.data.preferences.PreferenceEnum
import com.github.inlinefun.lazygo.data.preferences.PreferenceType
import com.github.inlinefun.lazygo.data.preferences.getPreferenceAsState
import com.github.inlinefun.lazygo.data.preferences.setPreference
import com.github.inlinefun.lazygo.util.LazyGOTheme
import kotlinx.coroutines.launch

@Composable
fun <T> LazyChoicePreference(
    @StringRes
    title: Int,
    @StringRes
    detail: Int? = null,
    @DrawableRes
    icon: Int,
    preference: PreferenceType.Choice<T>,
    index: Int = 0,
    count: Int = 1
) where T : PreferenceEnum, T : Enum<T> {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val value by context.getPreferenceAsState(preference)
    var expanded by remember { mutableStateOf(false) }
    LazyPreferenceItem(
        title,
        detail,
        icon,
        index,
        count,
        onClick = {
            expanded = !expanded
        },
        trailingContent = {
            Text(
                text = stringResource(id = value.label),
                style = MaterialTheme.typography.bodyMedium
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                preference.entries.forEach { entry ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(id = entry.label)
                            )
                        },
                        onClick = {
                            scope.launch {
                                context.setPreference(preference, value = entry)
                                expanded = false
                            }
                        }
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun PreviewLazyChoicePreference() {
    LazyGOTheme {
        LazyChoicePreference(
            title = R.string.pref_label_app_theme,
            detail = null,
            icon = R.drawable.palette,
            preference = LazyPreferences.Appearance.appTheme
        )
    }
}
