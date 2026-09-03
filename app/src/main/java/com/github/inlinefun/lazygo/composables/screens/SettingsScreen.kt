package com.github.inlinefun.lazygo.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.composables.components.navigation.LazyTopBar
import com.github.inlinefun.lazygo.composables.components.preferences.LazyPreferenceCategoryListItem
import com.github.inlinefun.lazygo.composables.components.preferences.PreferenceCategoryListItem
import com.github.inlinefun.lazygo.data.navigation.LazyNavRoute
import com.github.inlinefun.lazygo.data.navigation.LazySettingRoute
import com.github.inlinefun.lazygo.util.Constants
import com.github.inlinefun.lazygo.util.LazyGOTheme

@Composable
fun SettingsScreen(
    navigateTo: (LazySettingRoute) -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val categories = listOf(
        listOf(
            PreferenceCategoryListItem(
                title = R.string.label_appearance,
                detail = R.string.msg_appearance_preference_detail,
                icon = R.drawable.palette,
                route = LazyNavRoute.Settings.Appearance
            )
        ),
        listOf(
            PreferenceCategoryListItem(
                title = R.string.label_routes,
                detail = R.string.msg_routes_preference_detail,
                icon = R.drawable.directions,
                route = LazyNavRoute.Settings.Routes
            ),
            PreferenceCategoryListItem(
                title = R.string.label_activity,
                detail = R.string.msg_activity_preference_detail,
                icon = R.drawable.directions_run,
                route = LazyNavRoute.Settings.Activity
            )
        )
    )
    Scaffold(
        topBar = {
            LazyTopBar(
                title = R.string.label_settings,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(space = 2.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(all = Constants.Spacing.medium)
                .verticalScroll(scrollState)
        ) {
            categories.forEachIndexed { index, items ->
                items.forEachIndexed { index, item ->
                    LazyPreferenceCategoryListItem(
                        item = item,
                        onClick = {
                            navigateTo(item.route)
                        },
                        index = index,
                        count = items.size
                    )
                }
                if (index < (categories.size - 1)) {
                    Spacer(
                        modifier = Modifier
                            .height(height = Constants.Spacing.small)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    LazyGOTheme {
        SettingsScreen(
            navigateTo = { },
            onBack = { }
        )
    }
}
