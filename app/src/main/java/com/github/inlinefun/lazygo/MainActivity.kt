package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.github.inlinefun.lazygo.composables.navigation.LazyNavigationHost
import com.github.inlinefun.lazygo.util.LazyGOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyGOTheme {
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
