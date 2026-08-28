package com.github.inlinefun.lazygo.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.composables.LazyBaseScreen

@Composable
fun LazyNavigationRoot() {
    val backStack = rememberNavBackStack(LazyNavRoute.Base)
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyNavigationHost(
            backStack = backStack,
            entryProvider = entryProvider {
                entry<LazyNavRoute.Base> {
                    LazyBaseScreen()
                }
            }
        )
    }
}
