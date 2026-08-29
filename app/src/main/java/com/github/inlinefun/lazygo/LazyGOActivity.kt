package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.inlinefun.lazygo.common.LazyGOTheme
import com.github.inlinefun.lazygo.navigation.LazyNavigationRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LazyGOActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyGOTheme {
                LazyNavigationRoot()
            }
        }
    }
}
