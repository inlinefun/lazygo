package com.github.inlinefun.lazygo.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun LazyGOTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    amoledTheme: Boolean = false,
    content: @Composable () -> Unit
) = MaterialExpressiveTheme(
    colorScheme = LocalContext.current.let { context ->
        if (darkTheme) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    }.let { scheme ->
        if (amoledTheme && darkTheme) {
            scheme.copy(
                background = Color(0xFF000000)
            )
        } else {
            scheme
        }
    },
    content = content
)

@Composable
fun PreviewWrapper(
    content: @Composable () -> Unit
) = LazyGOTheme(
    content = content
)

@Composable
fun ScaffoldWrapper(
    content: @Composable () -> Unit
) = Scaffold { paddingValues ->
    Surface(
        content = content,
        modifier = Modifier
            .padding(paddingValues)
    )
}
