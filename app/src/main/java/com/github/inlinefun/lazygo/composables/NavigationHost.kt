package com.github.inlinefun.lazygo.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.common.NavRoute
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.components.NavHost
import com.github.inlinefun.lazygo.composables.root.RootContent
import com.github.inlinefun.lazygo.composables.settings.SettingsScreen

@Composable
fun NavigationHost() {
    val backStack = rememberNavBackStack(NavRoute.Root)
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            backStack = backStack,
            entryProvider = entryProvider {
                entry<NavRoute.Root> {
                    RootContent(
                        navigateTo = { route ->
                            backStack.add(route)
                        }
                    )
                }
                entry<NavRoute.Settings> {
                    SettingsScreen(
                        toPreviousScreen = {
                            backStack.removeLastOrNull()
                        }
                    )
                }
            }
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewNavigationHost() {
    PreviewWrapper {
        NavigationHost()
    }
}
