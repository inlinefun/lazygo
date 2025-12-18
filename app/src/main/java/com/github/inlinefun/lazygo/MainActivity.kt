package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.inlinefun.lazygo.composables.MapScreen
import com.github.inlinefun.lazygo.composables.SetupScreen
import com.github.inlinefun.lazygo.composables.SplashScreen
import com.github.inlinefun.lazygo.ui.AppTheme
import com.github.inlinefun.lazygo.ui.Navigation
import com.github.inlinefun.lazygo.ui.replace
import com.github.inlinefun.lazygo.viewmodels.AppViewModel
import com.github.inlinefun.lazygo.viewmodels.MapViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.NormalTheme)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                    ) { paddingValues ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            AppContent()

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()
    val model = viewModel<AppViewModel>()
    val mapModel = viewModel<MapViewModel>()
    NavHost(
        navController = navController,
        startDestination = Navigation.SplashScreen,
        enterTransition = {
            slideInHorizontally { it } + fadeIn()
        },
        popEnterTransition = {
            slideInHorizontally { -it } + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally { -it / 2 } + fadeOut()
        },
        popExitTransition = {
            slideOutHorizontally { it / 2 } + fadeOut()
        }
    ) {
        composable<Navigation.SplashScreen> {
            SplashScreen(
                viewModel = model,
                navigateToSetupScreen = {
                    navController.replace(Navigation.SetupScreen, Navigation.SplashScreen)
                },
                navigateToMapScreen = {
                    navController.replace(Navigation.MapScreen, Navigation.SplashScreen)
                }
            )
        }
        composable<Navigation.SetupScreen> {
            SetupScreen(
                viewModel = model,
                navigateToMapScreen = {
                    navController.replace(Navigation.MapScreen, Navigation.SetupScreen)
                }
            )
        }
        composable<Navigation.MapScreen> {
            MapScreen(
                viewModel = mapModel
            )
        }
    }
}