package com.github.inlinefun.lazygo.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

@Composable
fun <T : NavKey> NavHost(
    backStack: NavBackStack<T>,
    entryProvider: (T) -> NavEntry<T>,
    modifier: Modifier = Modifier
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
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider,
        modifier = modifier
    )
}
