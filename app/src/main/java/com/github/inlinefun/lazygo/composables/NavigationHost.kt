package com.github.inlinefun.lazygo.composables

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.github.inlinefun.lazygo.common.NavRoute
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.composables.root.RootContent
import com.github.inlinefun.lazygo.composables.settings.SettingsScreen

@Composable
fun NavigationHost() {
    val backStack = rememberNavBackStack(NavRoute.Root)
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val offset = 100
        NavDisplay(
            backStack = backStack,
            transitionSpec = {
                val entry = fadeIn() + slideInHorizontally { offset }
                val exit = fadeOut() + slideOutHorizontally { -offset }
                entry togetherWith exit
            },
            popTransitionSpec = {
                val entry = fadeIn() + slideInHorizontally { -offset }
                val exit = fadeOut() + slideOutHorizontally { offset }
                (entry togetherWith exit)
                    .apply {
                        targetContentZIndex = -1f
                    }
            },
            predictivePopTransitionSpec = {
                val entry = fadeIn() + slideInHorizontally { -offset }
                val exit = fadeOut() + slideOutHorizontally { offset }
                (entry togetherWith exit)
                    .apply {
                        targetContentZIndex = -1f
                    }
            },
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
