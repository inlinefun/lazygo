package com.github.inlinefun.lazygo.ui

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
sealed interface Navigation {
    @Serializable
    object SplashScreen: Navigation
    @Serializable
    object SetupScreen: Navigation
    @Serializable
    object MapScreen: Navigation
}

fun <A: Navigation, B: Navigation> NavHostController.replace(route: A, target: B) {
    navigate<A>(route) {
        popUpTo<B>(route = target) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun <A: Navigation> NavHostController.to(route: A) {
    navigate<A>(route) {
        launchSingleTop = true
    }
}
