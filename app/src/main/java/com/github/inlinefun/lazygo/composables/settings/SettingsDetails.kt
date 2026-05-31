package com.github.inlinefun.lazygo.composables.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.common.Constants
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.components.DefaultTopbar
import com.github.inlinefun.lazygo.composables.settings.components.ChoicePreferenceComponent
import com.github.inlinefun.lazygo.composables.settings.components.SettingComponentWrapper
import com.github.inlinefun.lazygo.composables.settings.components.SwitchPreferenceComponent
import com.github.inlinefun.lazygo.preferences.PreferenceKey
import com.github.inlinefun.lazygo.preferences.Preferences

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun SettingsDetails(
    toPreviousScreen: () -> Unit,
    preferences: Preferences
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            DefaultTopbar(
                title = preferences.label,
                onAction = toPreviousScreen
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(Constants.Spacing.small),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Constants.Spacing.medium)
                .padding(top = Constants.Spacing.medium)
                .verticalScroll(scrollState)
        ) {
            preferences.categories.forEach { preferenceCategory ->
                Text(
                    text = stringResource(preferenceCategory.label),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(Constants.Spacing.extraSmall),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val count = preferenceCategory.preferences.size
                    preferenceCategory.preferences.forEachIndexed { index, preferenceKey ->
                        when (preferenceKey) {
                            is PreferenceKey.Choice -> {
                                ChoicePreferenceComponent(
                                    key = preferenceKey,
                                    index = index,
                                    count = count
                                )
                            }
                            is PreferenceKey.Switch -> {
                                SwitchPreferenceComponent(
                                    key = preferenceKey,
                                    index = index,
                                    count = count
                                )
                            }

                            else -> {
                                SettingComponentWrapper(
                                    title = preferenceKey.label,
                                    description = preferenceKey.description,
                                    icon = preferenceKey.icon,
                                    onClick = {},
                                    index = index,
                                    count = count
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewSettingsDetails() {
    PreviewWrapper {
        SettingsDetails(
            toPreviousScreen = {},
            preferences = Preferences.Appearance
        )
    }
}
