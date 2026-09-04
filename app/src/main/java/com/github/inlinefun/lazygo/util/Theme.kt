package com.github.inlinefun.lazygo.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun LazyGOTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    amoledTheme: Boolean = false,
    content: @Composable () -> Unit
) = MaterialExpressiveTheme(
    colorScheme = LocalContext.current.let { context ->
        when (darkTheme) {
            true -> dynamicDarkColorScheme(context)
            false -> dynamicLightColorScheme(context)
        }
    }.let { colorScheme ->
        if (darkTheme && amoledTheme) {
            colorScheme.copy(
                background = Color(0xFF000000),
                surface = Color(0xFF000000)
            )
        } else {
            colorScheme
        }
    },
    content = content
)
