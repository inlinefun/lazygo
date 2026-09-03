package com.github.inlinefun.lazygo.composables.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.composables.components.navigation.LazyNavDisplay
import com.github.inlinefun.lazygo.composables.screens.ContentScreen
import com.github.inlinefun.lazygo.composables.screens.SettingsScreen
import com.github.inlinefun.lazygo.composables.screens.settings.ActivitySettingsScreen
import com.github.inlinefun.lazygo.composables.screens.settings.AppearanceSettingsScreen
import com.github.inlinefun.lazygo.composables.screens.settings.RoutesSettingsScreen
import com.github.inlinefun.lazygo.data.navigation.LazyNavRoute

@Composable
fun LazyNavigationHost() {
    val backStack = rememberNavBackStack(LazyNavRoute.Content)
    LazyNavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<LazyNavRoute.Content> {
                ContentScreen(
                    navigateTo = backStack::add
                )
            }
            entry<LazyNavRoute.Settings> {
                SettingsScreen(
                    navigateTo = backStack::add,
                    onBack = backStack::removeLastOrNull
                )
            }
            entry<LazyNavRoute.Settings.Appearance> {
                AppearanceSettingsScreen(
                    onBack = backStack::removeLastOrNull
                )
            }
            entry<LazyNavRoute.Settings.Routes> {
                RoutesSettingsScreen(
                    onBack = backStack::removeLastOrNull
                )
            }
            entry<LazyNavRoute.Settings.Activity> {
                ActivitySettingsScreen(
                    onBack = backStack::removeLastOrNull
                )
            }
        }
    )
}
