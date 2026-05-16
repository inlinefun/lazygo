package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.github.inlinefun.lazygo.common.LazyGoTheme
import com.github.inlinefun.lazygo.common.ScaffoldWrapper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val time = System.currentTimeMillis()
        val splash = this.installSplashScreen()
        splash.setKeepOnScreenCondition {
            System.currentTimeMillis() - time <= 1000
        }
        enableEdgeToEdge()

        setContent {
            LazyGoTheme {
                ScaffoldWrapper {
                }
            }
        }
    }
}