package com.github.inlinefun.lazygo.composables.settings

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.common.SettingsRoute
import com.github.inlinefun.lazygo.components.NavHost
import com.github.inlinefun.lazygo.preferences.Preferences.Appearance

@Composable
fun SettingsScreen(
    toPreviousScreen: () -> Unit
) {
    val backStack = rememberNavBackStack(SettingsRoute.SettingsList)
    NavHost(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<SettingsRoute.SettingsList> {
                SettingsList(
                    toPreviousScreen = toPreviousScreen,
                    navigateTo = { route ->
                        backStack.add(route)
                    }
                )
            }
            entry<SettingsRoute.SettingsDetails> { details ->
                val preferences = when (details.id) {
                    Appearance.id -> Appearance
                    else -> error("invalid preference route detail")
                }
                SettingsDetails(
                    toPreviousScreen = backStack::removeLastOrNull,
                    preferences = preferences
                )
            }
        }
    )
}
