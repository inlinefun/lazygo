package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.github.inlinefun.lazygo.common.LazyGOTheme
import com.github.inlinefun.lazygo.navigation.LazyNavigationRoot
import com.github.inlinefun.lazygo.preferences.AppTheme
import com.github.inlinefun.lazygo.preferences.Preferences
import com.github.inlinefun.lazygo.preferences.getPreferenceAsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LazyGOActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appTheme by applicationContext
                .getPreferenceAsState(key = Preferences.Appearance.appTheme)
            val systemInDarkTheme = isSystemInDarkTheme()
            val darkTheme = remember(key1 = appTheme, key2 = systemInDarkTheme) {
                when(appTheme) {
                    AppTheme.SYSTEM_DEFAULT -> systemInDarkTheme
                    AppTheme.DARK -> true
                    AppTheme.LIGHT -> false
                }
            }

            val amoledTheme by applicationContext
                .getPreferenceAsState(key = Preferences.Appearance.amoledTheme)
            LazyGOTheme(
                darkTheme = darkTheme,
                amoledTheme = amoledTheme
            ) {
                LazyNavigationRoot()
            }
        }
    }
}
