package com.github.inlinefun.lazygo.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.composables.LazyBaseScreen
import com.github.inlinefun.lazygo.composables.LazySettingsScreen
import com.github.inlinefun.lazygo.composables.settings.LazyAppearanceSettingsScreen

@Composable
fun LazyNavigationRoot() {
    val backStack = rememberNavBackStack(LazyNavRoute.Root)
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyNavigationHost(
            backStack = backStack,
            entryProvider = entryProvider {
                entry<LazyNavRoute.Root> {
                    LazyBaseScreen(
                        navigateTo = backStack::add
                    )
                }
                entry<LazyNavRoute.Settings> {
                    LazySettingsScreen(
                        navigateTo = backStack::add,
                        onBack = backStack::removeLastOrNull
                    )
                }
                entry<LazyNavRoute.Settings.AppearanceSettings> {
                    LazyAppearanceSettingsScreen(
                        onBack = backStack::removeLastOrNull
                    )
                }
            }
        )
    }
}
