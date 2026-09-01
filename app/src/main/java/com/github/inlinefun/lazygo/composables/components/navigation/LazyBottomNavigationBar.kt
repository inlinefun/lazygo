package com.github.inlinefun.lazygo.composables.components.navigation

import androidx.annotation.DrawableRes
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
import com.github.inlinefun.lazygo.data.navigation.LazyContentChoice
import com.github.inlinefun.lazygo.util.LazyGOTheme

private data class LazyBottomNavigationBarItem(
    @field:DrawableRes
    val icon: Int,
    @field:StringRes
    val label: Int,
    val choice: LazyContentChoice
)

@Composable
fun LazyBottomNavigationBar(
    currentChoice: NavKey,
    switchTo: (LazyContentChoice) -> Unit
) {
    val items = listOf(
        LazyBottomNavigationBarItem(
            icon = R.drawable.map,
            label = R.string.label_map,
            choice = LazyContentChoice.Map
        ),
        LazyBottomNavigationBarItem(
            icon = R.drawable.directions_run,
            label = R.string.label_activity,
            choice = LazyContentChoice.Activity
        )
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentChoice == item.choice,
                onClick = {
                    switchTo(item.choice)
                },
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
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLazyBottomNavigationBar() {
    LazyGOTheme {
        LazyBottomNavigationBar(
            currentChoice = LazyContentChoice.Map,
            switchTo = { }
        )
    }
}
