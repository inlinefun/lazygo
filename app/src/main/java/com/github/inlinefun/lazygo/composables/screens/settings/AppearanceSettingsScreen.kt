package com.github.inlinefun.lazygo.composables.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.composables.components.navigation.LazyTopBar
import com.github.inlinefun.lazygo.composables.components.preferences.LazyPreferenceCategory
import com.github.inlinefun.lazygo.data.preferences.LazyPreferences
import com.github.inlinefun.lazygo.util.Constants
import com.github.inlinefun.lazygo.util.LazyGOTheme

@Composable
fun AppearanceSettingsScreen(
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val categories = listOf(
        PreferenceCategory(
            title = R.string.label_interface,
            items = listOf(
                PreferenceItem(
                    title = R.string.pref_label_app_theme,
                    icon = R.drawable.dark_mode,
                    preference = LazyPreferences.Appearance.appTheme
                ),
                PreferenceItem(
                    title = R.string.pref_label_amoled_theme,
                    detail = R.string.pref_detail_amoled_theme,
                    icon = R.drawable.dark_mode,
                    preference = LazyPreferences.Appearance.amoledTheme
                )
            )
        ),
        PreferenceCategory(
            title = R.string.label_map,
            items = listOf(
                PreferenceItem(
                    title = R.string.pref_label_map_theme,
                    icon = R.drawable.map,
                    preference = LazyPreferences.Appearance.mapTheme
                ),
                PreferenceItem(
                    title = R.string.pref_label_map_type,
                    icon = R.drawable.map,
                    preference = LazyPreferences.Appearance.mapType
                )
            )
        )
    )
    Scaffold(
        topBar = {
            LazyTopBar(
                title = R.string.label_appearance,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(space = Constants.Spacing.medium),
            modifier = Modifier
                .padding(paddingValues)
                .padding(all = Constants.Spacing.medium)
                .verticalScroll(scrollState)
        ) {
            categories.forEach { category ->
                LazyPreferenceCategory(
                    category
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAppearanceSettingsScreen() {
    LazyGOTheme {
        AppearanceSettingsScreen(
            onBack = { }
        )
    }
}
