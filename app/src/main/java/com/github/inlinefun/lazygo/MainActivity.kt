package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.inlinefun.lazygo.composables.MapScreen
import com.github.inlinefun.lazygo.composables.SettingsScreen
import com.github.inlinefun.lazygo.composables.SetupScreen
import com.github.inlinefun.lazygo.composables.SplashScreen
import com.github.inlinefun.lazygo.data.UITheme
import com.github.inlinefun.lazygo.data.UserPreferences
import com.github.inlinefun.lazygo.ui.AppTheme
import com.github.inlinefun.lazygo.ui.Navigation
import com.github.inlinefun.lazygo.ui.go
import com.github.inlinefun.lazygo.ui.replace
import com.github.inlinefun.lazygo.viewmodels.AppViewModel
import com.github.inlinefun.lazygo.viewmodels.MapViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val view = LocalView.current
            val amoledMode by remember {
                (applicationContext as MainApplication)
                    .preferencesStore
                    .flow(UserPreferences.AmoledTheme)
            }.collectAsState(initial = UserPreferences.AmoledTheme.defaultValue)
            val appTheme by remember {
                (applicationContext as MainApplication)
                    .preferencesStore
                    .flow(UserPreferences.AppTheme)
            }.collectAsState(initial = UserPreferences.AppTheme.defaultValue)
            val systemInDarkMode = isSystemInDarkTheme()
            val useDarkMode by remember {
                derivedStateOf {
                    when(appTheme) {
                        UITheme.AUTO -> systemInDarkMode
                        UITheme.DARK -> true
                        UITheme.LIGHT -> false
                    }
                }
            }
            LaunchedEffect(appTheme) {
                WindowCompat
                    .getInsetsController(this@MainActivity.window, view)
                    .isAppearanceLightStatusBars = !useDarkMode
            }
            AppTheme(
                darkMode = useDarkMode,
                amoledTheme = amoledMode
            ) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxSize()
                ) { paddingValues ->
                    AppContent(
                        modifier = Modifier
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun AppContent(
    modifier: Modifier = Modifier
) {
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
        },
        modifier = modifier
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
                viewModel = mapModel,
                navigateToSettingsScreen = {
                    navController.go(Navigation.SettingsScreen)
                }
            )
        }
        composable<Navigation.SettingsScreen> {
            SettingsScreen(
                preferencesStore = model.preferences
            )
        }
    }
}