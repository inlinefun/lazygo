package com.github.inlinefun.lazygo.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.github.inlinefun.lazygo.common.NavRoute
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.composables.root.RootContent

@Composable
fun NavigationHost() {
    val backStack = rememberNavBackStack(NavRoute.Root)
    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<NavRoute.Root> {
                RootContent()
            }
        }
    )
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
