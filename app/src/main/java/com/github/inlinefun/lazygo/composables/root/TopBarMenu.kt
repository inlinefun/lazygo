package com.github.inlinefun.lazygo.composables.root

import androidx.compose.material3.DropdownMenuGroup
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenuPopup
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.NavRoute

@Composable
@Suppress("AssignedValueIsNeverRead") // because it is
fun TopBarMenu(
    navigateTo: (NavRoute) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.more_vert),
            contentDescription = null
        )
    }
    DropdownMenuPopup(
        expanded = expanded,
        onDismissRequest = {
            expanded = false
        }
    ) {
        DropdownMenuGroup(
            shapes = MenuDefaults.groupShape(0, 1)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.label_settings)
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.settings),
                        contentDescription = null
                    )
                },
                onClick = {
                    expanded = false
                    navigateTo(NavRoute.Settings)
                }
            )
        }
    }
}
