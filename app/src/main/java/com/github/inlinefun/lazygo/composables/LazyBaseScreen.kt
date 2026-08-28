package com.github.inlinefun.lazygo.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.components.LazyBottomNavigationBar
import com.github.inlinefun.lazygo.components.special.SpecialLazyTopBar
import com.github.inlinefun.lazygo.navigation.LazyBaseRoute
import com.github.inlinefun.lazygo.navigation.LazyNavRoute
import com.github.inlinefun.lazygo.navigation.LazyNavigationHost

@Composable
fun LazyBaseScreen(
    navigateTo: (LazyNavRoute) -> Unit
) {
    val baseBackstack = rememberNavBackStack(LazyBaseRoute.Map)
    val currentBaseRoute by remember {
        derivedStateOf {
            baseBackstack.last()
        }
    }
    Scaffold(
        topBar = {
            SpecialLazyTopBar(
                navigateTo = { route ->
                    navigateTo(route)
                }
            )
        },
        bottomBar = {
            LazyBottomNavigationBar(
                currentRoute = currentBaseRoute,
                navigateTo = { route ->
                    when(route) {
                        is LazyBaseRoute.Map -> {
                            baseBackstack.removeAll { it != route }
                        }
                        else -> {
                            baseBackstack.add(
                                index = 1,
                                element = route
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyNavigationHost(
            backStack = baseBackstack,
            modifier = Modifier
                .padding(paddingValues),
            entryProvider = entryProvider {
                entry<LazyBaseRoute.Map> {
                    Surface {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.label_map)
                            )
                        }
                    }
                }
                entry<LazyBaseRoute.Activity> {
                    Surface {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.label_activity)
                            )
                        }
                    }
                }
            },
        )
    }
}

@Preview
@Composable
private fun PreviewLazyBaseScreen() {
    PreviewWrapper {
        LazyBaseScreen(
            navigateTo = {  }
        )
    }
}
