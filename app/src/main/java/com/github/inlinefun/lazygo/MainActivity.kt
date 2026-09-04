package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.github.inlinefun.lazygo.composables.navigation.LazyNavigationHost
import com.github.inlinefun.lazygo.data.preferences.LazyPreferences
import com.github.inlinefun.lazygo.data.preferences.PreferenceAppTheme
import com.github.inlinefun.lazygo.data.preferences.getPreferenceAsState
import com.github.inlinefun.lazygo.util.LazyGOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appTheme by applicationContext
                .getPreferenceAsState(
                    preference = LazyPreferences.Appearance.appTheme
                )
            LazyGOTheme(
                darkTheme = when (appTheme) {
                    PreferenceAppTheme.SYSTEM_DEFAULT -> isSystemInDarkTheme()
                    PreferenceAppTheme.DARK -> true
                    PreferenceAppTheme.LIGHT -> false
                }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    LazyNavigationHost()
                }
            }
        }
    }
}
