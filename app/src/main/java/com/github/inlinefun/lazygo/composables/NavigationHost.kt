package com.github.inlinefun.lazygo.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.common.NavRoute
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.components.NavHost
import com.github.inlinefun.lazygo.composables.root.RootContent
import com.github.inlinefun.lazygo.composables.settings.SettingsScreen
import com.github.inlinefun.lazygo.viewmodel.MapViewModel

@Composable
fun NavigationHost() {
    val backStack = rememberNavBackStack(NavRoute.Root)
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val owner = LocalViewModelStoreOwner.current ?: error("No viewmodel store owner")
        hiltViewModel<MapViewModel>(viewModelStoreOwner = owner)

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
