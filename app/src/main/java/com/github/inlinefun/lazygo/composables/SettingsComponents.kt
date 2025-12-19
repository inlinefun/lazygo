package com.github.inlinefun.lazygo.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.inlinefun.lazygo.data.BooleanPreferenceKey
import com.github.inlinefun.lazygo.data.EnumPreferenceKey
import com.github.inlinefun.lazygo.data.PreferenceEnum
import com.github.inlinefun.lazygo.data.PreferenceSerializer
import com.github.inlinefun.lazygo.data.PreferencesStore
import com.github.inlinefun.lazygo.ui.Constants
import com.github.inlinefun.lazygo.util.displayName
import kotlinx.coroutines.launch

@Composable
fun BooleanPreferenceComponent(
    preferenceKey: BooleanPreferenceKey,
    preferencesStore: PreferencesStore
) {
    val scope = rememberCoroutineScope()
    val value by preferencesStore.flow(preferenceKey).collectAsState(
        preferenceKey.defaultValue
    )
    val onClick: (Boolean) -> Unit = { value ->
        scope.launch {
            preferencesStore.set(preferenceKey, value)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    onClick(!value)
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(Constants.Padding.small)
                .height(Constants.Sizing.medium)
        ) {
            Text(
                text = stringResource(preferenceKey.label)
            )
            Spacer(
                modifier = Modifier
                    .weight(1.0f)
            )
            Switch(
                checked = value,
                onCheckedChange = onClick
            )
        }
    }
}

@Composable
fun EnumPreferenceComponent(
    preferenceKey: EnumPreferenceKey<PreferenceEnum>,
    preferencesStore: PreferencesStore
) {
    val scope = rememberCoroutineScope()
    var opened by remember { mutableStateOf(false) }
    val value by preferencesStore.flow(preferenceKey).collectAsState(
        preferenceKey.defaultValue
    )
    val entries = (preferenceKey.serializer as PreferenceSerializer.EnumSerializer).values
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    opened = !opened
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(Constants.Padding.small)
                .height(Constants.Sizing.medium)
        ) {
            Text(
                text = stringResource(preferenceKey.label)
            )
            Spacer(
                modifier = Modifier
                    .weight(1.0f)
            )
            Box {
                AnimatedContent(
                    targetState = stringResource(value.resourceId)
                ) { target ->
                    Text(
                        text = target
                    )
                }
                DropdownMenu(
                    expanded = opened,
                    onDismissRequest = { opened = false }
                ) {
                    entries.forEach { entry ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(entry.resourceId)
                                )
                            },
                            onClick = {
                                scope.launch {
                                    preferencesStore.set(preferenceKey, entry)
                                    opened = false
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
