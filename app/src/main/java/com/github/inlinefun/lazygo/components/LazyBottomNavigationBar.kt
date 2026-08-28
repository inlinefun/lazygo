package com.github.inlinefun.lazygo.components

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.navigation.LazyBaseRoute

private data class LazyBottomNavigationBarItem(
    @get:StringRes
    val label: Int,
    @get:StringRes
    val icon: Int,
    val route: LazyBaseRoute
)

@Composable
fun LazyBottomNavigationBar(
    currentRoute: NavKey,
    navigateTo: (LazyBaseRoute) -> Unit
) {
    val items = listOf(
        LazyBottomNavigationBarItem(
            label = R.string.label_map,
            icon = R.drawable.map,
            route = LazyBaseRoute.Map
        ),
        LazyBottomNavigationBarItem(
            label = R.string.label_activity,
            icon = R.drawable.directions_run,
            route = LazyBaseRoute.Activity
        )
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.label)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.label)
                    )
                },
                onClick = {
                    navigateTo(item.route)
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLazyBottomNavigationBar() {
    PreviewWrapper {
        LazyBottomNavigationBar(
            currentRoute = LazyBaseRoute.Map,
            navigateTo = {  }
        )
    }
}
