package com.github.inlinefun.lazygo.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.PreviewWrapper
import com.github.inlinefun.lazygo.components.LazyTopBar
import com.github.inlinefun.lazygo.navigation.LazyNavRoute

private data class BasePreferenceGroup(
    @field:StringRes
    val label: Int,
    val items: List<BasePreferenceCategory>
)

private data class BasePreferenceCategory(
    @field:StringRes
    val label: Int,
    @field:DrawableRes
    val icon: Int,
    val route: LazyNavRoute,
)

@Composable
fun LazySettingsScreen(
    navigateTo: (LazyNavRoute) -> Unit,
    onBack: () -> Unit
) {
    val groups = listOf(
        BasePreferenceGroup(
            label = R.string.pref_grp_interface,
            items = listOf(
                BasePreferenceCategory(
                    label = R.string.pref_cat_appearance,
                    icon = R.drawable.palette,
                    route = LazyNavRoute.Settings.AppearanceSettings
                )
            )
        )
    )
    val scrollState = rememberScrollState(initial = 0)
    Scaffold(
        topBar = {
            LazyTopBar(
                title = R.string.label_settings,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            groups.forEach { group ->
                PreferenceGroup(group, navigateTo)
            }
        }
    }
}

@Composable
private fun PreferenceGroup(
    group: BasePreferenceGroup,
    navigateTo: (LazyNavRoute) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(id = group.label),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(start = 4.dp, bottom = 4.dp)
        )
        group
            .items
            .forEachIndexed { index, item ->
                PreferenceCategory(item, navigateTo, index, count = group.items.size)
            }
    }
}

@Composable
private fun PreferenceCategory(
    item: BasePreferenceCategory,
    navigateTo: (LazyNavRoute) -> Unit,
    index: Int = 0,
    count: Int = 1
) {
    SegmentedListItem(
        leadingContent = {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            percent = 25
                        )
                    )
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .padding(all = 12.dp)
            )
        },
        onClick = {
            navigateTo(item.route)
        },
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(
            all = 4.dp
        ),
        shapes = ListItemDefaults.segmentedShapes(
            index = index,
            count = count
        ),
        colors = ListItemDefaults.segmentedColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = stringResource(id = item.label),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun PreviewLazySettingsScreen() {
    PreviewWrapper {
        LazySettingsScreen(
            navigateTo = { },
            onBack = { }
        )
    }
}