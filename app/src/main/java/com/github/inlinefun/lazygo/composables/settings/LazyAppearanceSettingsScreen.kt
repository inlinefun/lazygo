package com.github.inlinefun.lazygo.composables.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.components.LazyTopBar
import com.github.inlinefun.lazygo.components.special.PreferenceItem
import com.github.inlinefun.lazygo.preferences.Preferences

@Composable
fun LazyAppearanceSettingsScreen(
    onBack: () -> Unit
) {
    val groups = listOf(
        PreferenceGroup(
            label = R.string.pref_grp_app,
            items = listOf(
                PreferenceItem(
                    label = R.string.pref_opt_app_theme,
                    icon = R.drawable.palette,
                    preference = Preferences.Appearance.appTheme
                ),
                PreferenceItem(
                    label = R.string.pref_opt_amoled_theme,
                    icon = R.drawable.dark_mode,
                    preference = Preferences.Appearance.amoledTheme
                )
            )
        )
    )
    val scrollState = rememberScrollState(initial = 0)
    Scaffold(
        topBar = {
            LazyTopBar(
                title = R.string.pref_cat_appearance,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            groups
                .forEach { group ->
                    PreferenceGroup(group)
                }
        }
    }
}

@Preview
@Composable
private fun PreviewLazyAppearanceSettingsScreen() {
    PreviewWrapper {
        LazyAppearanceSettingsScreen(
            onBack = { }
        )
    }
}
