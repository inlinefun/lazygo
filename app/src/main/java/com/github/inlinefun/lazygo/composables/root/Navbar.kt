package com.github.inlinefun.lazygo.composables.root

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.SubRoute

data class SubRouteNavItem(
    @get:StringRes
    val label: Int,
    @get:DrawableRes
    val icon: Int,
    val route: SubRoute
)

@Composable
fun BottomNavbar(
    currentRoute: NavKey,
    navigateTo: (SubRoute) -> Unit
) {
    val items = listOf(
        SubRouteNavItem(
            label = R.string.label_nav_map,
            icon = R.drawable.globe_location_pin,
            route = SubRoute.Map
        ),
        SubRouteNavItem(
            label = R.string.label_nav_activity,
            icon = R.drawable.directions_run,
            route = SubRoute.Activity
        )
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.label)
                    )
                },
                onClick = {
                    navigateTo(item.route)
                }
            )
        }
    }
}