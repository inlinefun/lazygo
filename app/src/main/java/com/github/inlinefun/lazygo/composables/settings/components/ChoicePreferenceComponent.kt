package com.github.inlinefun.lazygo.composables.settings.components

import androidx.compose.animation.AnimatedContent
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
import com.github.inlinefun.lazygo.preferences.PreferenceEnum
import com.github.inlinefun.lazygo.preferences.PreferenceKey
import com.github.inlinefun.lazygo.preferences.getPreferenceAsState
import com.github.inlinefun.lazygo.preferences.setPreference
import kotlinx.coroutines.launch

@Composable
fun <T> ChoicePreferenceComponent(
    key: PreferenceKey.Choice<T>,
    index: Int = 0,
    count: Int = 1
) where T : PreferenceEnum, T : Enum<T> {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(value = false) }
    val value by context
        .getPreferenceAsState(key)
    SettingComponentWrapper(
        title = key.label,
        description = key.description,
        icon = key.icon,
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
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                key.enumEntries.forEach { entry ->
                    DropdownMenuItem(
                        onClick = {
                            scope.launch {
                                println(entry)
                                context
                                    .setPreference(key, entry)
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
