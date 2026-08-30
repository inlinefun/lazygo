package com.github.inlinefun.lazygo.components.special

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.preferences.Preference
import com.github.inlinefun.lazygo.preferences.PreferenceEnum
import com.github.inlinefun.lazygo.preferences.PreferenceKey
import com.github.inlinefun.lazygo.preferences.Preferences
import com.github.inlinefun.lazygo.preferences.getPreferenceAsState
import com.github.inlinefun.lazygo.preferences.setPreference
import kotlinx.coroutines.launch

data class PreferenceItem<T>(
    val label: Int,
    val icon: Int,
    val preference: T
) where T : Preference, T : PreferenceKey<*, *>

@Composable
fun SwitchPreferenceItem(
    @DrawableRes
    icon: Int,
    @StringRes
    title: Int,
    preference: PreferenceKey.Switch,
    index: Int = 0,
    count: Int = 1
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val value by context
        .getPreferenceAsState(key = preference)
    LazyPreferenceComponentWrapper(
        title = title,
        description = null,
        icon = icon,
        count = count,
        index = index,
        onClick = {
            scope.launch {
                context
                    .setPreference(preference, !value)
            }
        },
        trailingContent = {
            Switch(
                checked = value,
                onCheckedChange = null,
                thumbContent = {
                    AnimatedContent(
                        targetState = if (value) R.drawable.check else R.drawable.close
                    ) { icon ->
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                },
                modifier = Modifier
                    .padding(all = 8.dp)
            )
        }
    )
}

@Composable
fun <T> ChoicePreferenceItem(
    @DrawableRes
    icon: Int,
    @StringRes
    title: Int,
    preference: PreferenceKey.Choice<T>,
    index: Int = 0,
    count: Int = 1
) where T : PreferenceEnum, T : Enum<T> {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(value = false) }
    val value by context
        .getPreferenceAsState(key = preference)
    LazyPreferenceComponentWrapper(
        title = title,
        description = null,
        icon = icon,
        count = count,
        index = index,
        onClick = {
            expanded = !expanded
        },
        trailingContent = {
            AnimatedContent(
                targetState = stringResource(id = value.label)
            ) { text ->
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(all = 8.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                preference.entries.forEach { entry ->
                    DropdownMenuItem(
                        onClick = {
                            scope.launch {
                                println(entry)
                                context
                                    .setPreference(preference, entry)
                                expanded = false
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(id = entry.label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun LazyPreferenceComponentWrapper(
    @StringRes
    title: Int,
    @StringRes
    description: Int? = null,
    @DrawableRes
    icon: Int? = null,
    trailingContent: @Composable () -> Unit = { },
    extraContent: @Composable () -> Unit = { },
    onClick: () -> Unit = { },
    index: Int = 0,
    count: Int = 1
) {
    SegmentedListItem(
        leadingContent = {
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .clip(
                            shape = RoundedCornerShape(
                                percent = 25
                            )
                        )
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(all = 12.dp)
                )
            }
        },
        supportingContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (description != null) {
                    Text(
                        text = stringResource(id = description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                extraContent()
            }
        },
        trailingContent = trailingContent,
        onClick = onClick,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(
            all = 4.dp
        ),
        shapes = ListItemDefaults.segmentedShapes(
            index = index,
            count = count
        ),
        colors = ListItemDefaults.segmentedColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun PreviewSwitchPreferenceItem() {
    PreviewWrapper {
        SwitchPreferenceItem(
            icon = R.drawable.dark_mode,
            title = R.string.pref_opt_amoled_theme,
            preference = Preferences.Appearance.amoledTheme
        )
    }
}

@Preview
@Composable
private fun PreviewChoicePreferenceItem() {
    PreviewWrapper {
        ChoicePreferenceItem(
            icon = R.drawable.palette,
            title = R.string.pref_opt_app_theme,
            preference = Preferences.Appearance.appTheme
        )
    }
}
