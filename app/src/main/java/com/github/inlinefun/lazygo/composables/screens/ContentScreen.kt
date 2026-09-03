package com.github.inlinefun.lazygo.composables.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.composables.components.navigation.LazyBottomNavigationBar
import com.github.inlinefun.lazygo.composables.components.navigation.LazyContentTopBar
import com.github.inlinefun.lazygo.composables.components.navigation.LazyNavDisplay
import com.github.inlinefun.lazygo.composables.screens.content.ActivityContent
import com.github.inlinefun.lazygo.composables.screens.content.MapContent
import com.github.inlinefun.lazygo.data.navigation.LazyContentChoice
import com.github.inlinefun.lazygo.data.navigation.LazyNavRoute

@Composable
fun ContentScreen(
    navigateTo: (LazyNavRoute) -> Unit
) {
    val contentBackStack = rememberNavBackStack(LazyContentChoice.Map)
    val currentChoice by remember {
        derivedStateOf(contentBackStack::last)
    }
    Scaffold(
        topBar = {
            LazyContentTopBar(
                navigateToSettings = {
                    navigateTo(LazyNavRoute.Settings)
                }
            )
        },
        bottomBar = {
            LazyBottomNavigationBar(
                currentChoice = currentChoice,
                switchTo = { choice ->
                    when (choice) {
                        LazyContentChoice.Map -> contentBackStack.removeAll { it != choice }
                        else -> contentBackStack.add(index = 1, element = choice)
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyNavDisplay(
            backStack = contentBackStack,
            modifier = Modifier
                .padding(paddingValues),
            entryProvider = entryProvider {
                entry<LazyContentChoice.Map> {
                    MapContent()
                }
                entry<LazyContentChoice.Activity> {
                    ActivityContent()
                }
            }
        )
    }
}
