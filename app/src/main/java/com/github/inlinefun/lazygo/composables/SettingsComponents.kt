package com.github.inlinefun.lazygo.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
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
import androidx.compose.ui.util.fastRoundToInt
import com.github.inlinefun.lazygo.data.BooleanPreferenceKey
import com.github.inlinefun.lazygo.data.EnumPreferenceKey
import com.github.inlinefun.lazygo.data.FloatPreferenceKey
import com.github.inlinefun.lazygo.data.IntPreferenceKey
import com.github.inlinefun.lazygo.data.PreferenceEnum
import com.github.inlinefun.lazygo.data.PreferenceSerializer
import com.github.inlinefun.lazygo.data.PreferencesStore
import com.github.inlinefun.lazygo.ui.Constants
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
@OptIn(ExperimentalMaterial3Api::class)
fun IntPreferenceComponent(
    preferenceKey: IntPreferenceKey,
    preferencesStore: PreferencesStore
) {
    val scope = rememberCoroutineScope()
    val value by preferencesStore
        .flow(preferenceKey)
        .collectAsState(
            initial = preferenceKey.defaultValue
        )
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(Constants.Padding.small)
        ) {
            Text(
                text = stringResource(preferenceKey.label)
            )
            Spacer(
                modifier = Modifier
                    .weight(1.0f)
            )
            Text(
                text = "${preferenceKey.prefix ?: ""} $value ${preferenceKey.suffix ?: ""}"
            )
        }
        Slider(
            value = value.toFloat(),
            valueRange = preferenceKey.minimum.toFloat()..preferenceKey.maximum.toFloat(),
            onValueChange = { value ->
                scope.launch {
                    preferencesStore
                        .set(preferenceKey, value.fastRoundToInt())
                }
            },
            modifier = Modifier
                .padding(horizontal = Constants.Padding.medium, vertical = Constants.Padding.small)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FloatPreferenceKey(
    preferenceKey: FloatPreferenceKey,
    preferencesStore: PreferencesStore
) {
    val scope = rememberCoroutineScope()
    val value by preferencesStore
        .flow(preferenceKey)
        .collectAsState(
            initial = preferenceKey.defaultValue
        )
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(Constants.Padding.small)
        ) {
            Text(
                text = stringResource(preferenceKey.label)
            )
            Spacer(
                modifier = Modifier
                    .weight(1.0f)
            )
            Text(
                text = "${preferenceKey.prefix ?: ""} $value ${preferenceKey.suffix ?: ""}"
            )
        }
        Slider(
            value = value,
            valueRange = preferenceKey.minimum..preferenceKey.maximum,
            onValueChange = { value ->
                scope.launch {
                    preferencesStore
                        .set(preferenceKey, value)
                }
            },
            modifier = Modifier
                .padding(horizontal = Constants.Padding.medium, vertical = Constants.Padding.small)
        )
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
