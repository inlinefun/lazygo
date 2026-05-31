package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.github.inlinefun.lazygo.common.LazyGoTheme
import com.github.inlinefun.lazygo.composables.NavigationHost
import com.github.inlinefun.lazygo.preferences.AppTheme
import com.github.inlinefun.lazygo.preferences.Preferences
import com.github.inlinefun.lazygo.preferences.getPreferenceAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appTheme by applicationContext
                .getPreferenceAsState(key = Preferences.Appearance.appTheme)
            val systemInDarkTheme = isSystemInDarkTheme()
            val darkTheme = remember(appTheme, systemInDarkTheme) {
                when (appTheme) {
                    AppTheme.SYSTEM_DEFAULT -> systemInDarkTheme
                    AppTheme.DARK -> true
                    AppTheme.LIGHT -> false
                }
            }

            val amoledTheme by applicationContext
                .getPreferenceAsState(key = Preferences.Appearance.amoledTheme)

            LazyGoTheme(
                darkTheme = darkTheme,
                amoledTheme = amoledTheme
            ) {
                NavigationHost()
            }
        }
    }
}
