package com.github.inlinefun.lazygo.composables.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.Constants
import com.github.inlinefun.lazygo.common.SettingsRoute
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.components.DefaultTopbar
import com.github.inlinefun.lazygo.preferences.Preferences

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun SettingsList(
    toPreviousScreen: () -> Unit,
    navigateTo: (SettingsRoute) -> Unit
) {
    val preferences = listOf<Preferences>(
        Preferences.Appearance
    )
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            DefaultTopbar(
                title = R.string.label_settings,
                onAction = toPreviousScreen
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(Constants.Spacing.small),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Constants.Spacing.medium)
                .padding(top = Constants.Spacing.medium)
                .verticalScroll(scrollState)
        ) {
            preferences.forEach { preferences ->
                ListItem(
                    onClick = {
                        SettingsRoute.SettingsDetails(
                            id = preferences.id
                        ).let { route ->
                            navigateTo(route)
                        }
                    },
                    leadingContent = {
                        Icon(
                            painter = painterResource(id = preferences.icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .padding(Constants.Spacing.small)
                                .clip(
                                    shape = RoundedCornerShape(
                                        percent = 25
                                    )
                                )
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(Constants.Spacing.small)
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    contentPadding = PaddingValues(
                        all = Constants.Spacing.small
                    ),
                    shapes = ListItemDefaults.shapes(
                        shape = ListItemDefaults.shapes().selectedShape
                    )
                ) {
                    Text(
                        text = stringResource(id = preferences.label),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewSettingsList() {
    PreviewWrapper {
        SettingsList(
            toPreviousScreen = {},
            navigateTo = {}
        )
    }
}
