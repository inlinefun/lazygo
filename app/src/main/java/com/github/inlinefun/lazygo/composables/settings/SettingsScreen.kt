package com.github.inlinefun.lazygo.composables.settings

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.common.SettingsRoute
import com.github.inlinefun.lazygo.components.NavHost

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
            entry<SettingsRoute.SettingsDetails> { data ->
                SettingsDetails(
                    toPreviousScreen = backStack::removeLastOrNull,
                    data = data
                )
            }
        }
    )
}
