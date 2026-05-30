package com.github.inlinefun.lazygo.composables.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import com.github.inlinefun.lazygo.common.NavRoute
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.common.SubRoute
import com.github.inlinefun.lazygo.components.NavHost
import com.github.inlinefun.lazygo.composables.root.activity.ActivityWrapper
import com.github.inlinefun.lazygo.composables.root.map.MapWrapper

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RootContent(
    navigateTo: (NavRoute) -> Unit
) {
    val contentBackStack = rememberNavBackStack(SubRoute.Map)
    val currentRoute by remember {
        derivedStateOf {
            contentBackStack.last()
        }
    }
    Scaffold(
        topBar = {
            TopBar(navigateTo)
        },
        bottomBar = {
            BottomNavbar(
                currentRoute = currentRoute,
                navigateTo = { route ->
                    when (route) {
                        is SubRoute.Map -> {
                            contentBackStack.removeIf { it != route }
                        }

                        is SubRoute.Activity -> {
                            contentBackStack.add(route)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            backStack = contentBackStack,
            entryProvider = entryProvider {
                entry<SubRoute.Map> {
                    MapWrapper(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
                entry<SubRoute.Activity> {
                    ActivityWrapper(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewRootContent() {
    PreviewWrapper {
        RootContent(
            navigateTo = {}
        )
    }
}
