package com.github.inlinefun.lazygo.components.special

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.preferences.Preference
import com.github.inlinefun.lazygo.preferences.PreferenceEnum
import com.github.inlinefun.lazygo.preferences.PreferenceKey
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
    shape: RoundedCornerShape,
    preference: PreferenceKey.Switch
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val value by context.getPreferenceAsState(key = preference)
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        onClick = {
            scope.launch {
                context.setPreference(
                    key = preference,
                    value = !value
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(all = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(all = 4.dp)
                    .clip(shape = RoundedCornerShape(percent = 33))
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(
                            alpha = 0.3f
                        )
                    )
                    .padding(all = 12.dp)
            )
            Text(
                text = stringResource(id = title)
            )
            Spacer(
                modifier = Modifier
                    .weight(1.0f)
            )
            Switch(
                checked = value,
                onCheckedChange = {
                    scope.launch {
                        context.setPreference(
                            key = preference,
                            value = !value
                        )
                    }
                },
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
                    .padding(all = 4.dp)
            )
        }
    }
}

@Composable
fun <T> ChoicePreferenceItem(
    @DrawableRes
    icon: Int,
    @StringRes
    title: Int,
    shape: RoundedCornerShape,
    preference: PreferenceKey.Choice<T>
) where T : PreferenceEnum, T : Enum<T> {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val value by context.getPreferenceAsState(key = preference)
    var expanded by remember { mutableStateOf(false) }
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        onClick = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(all = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(all = 4.dp)
                    .clip(shape = RoundedCornerShape(percent = 33))
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(
                            alpha = 0.3f
                        )
                    )
                    .padding(all = 12.dp)
            )
            Text(
                text = stringResource(id = title)
            )
            Spacer(
                modifier = Modifier
                    .weight(1.0f)
            )
            AnimatedContent(
                targetState = value.label
            ) { label ->
                Text(
                    text = stringResource(id = label),
                    modifier = Modifier
                        .padding(all = 4.dp)
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
                                    context
                                        .setPreference(
                                            key = preference,
                                            value = entry
                                        )
                                    expanded = false
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
