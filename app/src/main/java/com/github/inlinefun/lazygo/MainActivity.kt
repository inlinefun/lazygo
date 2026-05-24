package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.github.inlinefun.lazygo.common.LazyGoTheme
import com.github.inlinefun.lazygo.composables.NavigationHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyGoTheme(
                darkTheme = isSystemInDarkTheme(),
                amoledTheme = false
            ) {
                NavigationHost()
            }
        }
    }
}
