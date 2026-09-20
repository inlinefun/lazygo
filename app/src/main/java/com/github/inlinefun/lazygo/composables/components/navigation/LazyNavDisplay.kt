package com.github.inlinefun.lazygo.composables.components.navigation

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
import androidx.navigationevent.NavigationEvent

@Composable
fun <T> LazyNavDisplay(
    backStack: NavBackStack<T>,
    entryProvider: (T) -> NavEntry<T>,
    modifier: Modifier = Modifier
) where T : NavKey {
    val offset = 100
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryProvider = entryProvider,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        transitionSpec = {
            val entryTransition = fadeIn() + slideInHorizontally { offset }
            val exitTransition = fadeOut() + slideOutHorizontally { -offset }

            entryTransition togetherWith exitTransition
        },
        popTransitionSpec = {
            val entryTransition = fadeIn() + slideInHorizontally { -offset }
            val exitTransition = fadeOut() + slideOutHorizontally { offset }

            (entryTransition togetherWith exitTransition)
                .apply {
                    targetContentZIndex = -1f
                }
        },
        predictivePopTransitionSpec = { swipeEdge ->
            val modifier = when(swipeEdge) {
                NavigationEvent.EDGE_LEFT -> 1
                NavigationEvent.EDGE_RIGHT -> -1
                else -> 1
            }
            val entryTransition = fadeIn() + slideInHorizontally { -offset * modifier }
            val exitTransition = fadeOut() + slideOutHorizontally { offset * modifier }

            (entryTransition togetherWith exitTransition)
                .apply {
                    targetContentZIndex = -1f
                }
        }
    )
}
