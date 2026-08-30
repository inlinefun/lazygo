package com.github.inlinefun.lazygo.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
                val shape = when {
                    group.items.size == 1 -> RoundedCornerShape(size = 24.dp)
                    index == 0 -> RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomStart = 6.dp,
                        bottomEnd = 6.dp
                    )

                    group.items.size == (index + 1) -> RoundedCornerShape(
                        topStart = 6.dp,
                        topEnd = 6.dp,
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )

                    else -> RoundedCornerShape(size = 6.dp)
                }
                PreferenceCategory(shape, item, navigateTo)
            }
    }
}

@Composable
private fun PreferenceCategory(
    shape: RoundedCornerShape,
    item: BasePreferenceCategory,
    navigateTo: (LazyNavRoute) -> Unit
) {
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        onClick = {
            navigateTo(item.route)
        },
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(all = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(all = 4.dp)
                    .clip(shape = RoundedCornerShape(percent = 33))
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(
                            alpha = 0.3f
                        )
                    )
                    .padding(all = 12.dp)
            )
            Text(
                text = stringResource(id = item.label)
            )
        }
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