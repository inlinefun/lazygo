package com.github.inlinefun.lazygo.composables.components.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
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
import com.github.inlinefun.lazygo.data.navigation.LazyNavRoute
import com.github.inlinefun.lazygo.data.navigation.LazySettingRoute
import com.github.inlinefun.lazygo.util.LazyGOTheme

data class PreferenceCategoryListItem(
    @field:StringRes
    val title: Int,
    @field:StringRes
    val detail: Int? = null,
    @field:DrawableRes
    val icon: Int,
    val route: LazySettingRoute,
)

@Composable
fun LazyPreferenceCategoryListItem(
    item: PreferenceCategoryListItem,
    onClick: () -> Unit,
    count: Int = 1,
    index: Int = 0
) {
    SegmentedListItem(
        onClick = onClick,
        verticalAlignment = Alignment.CenterVertically,
        shapes = ListItemDefaults.segmentedShapes(
            count = count,
            index = index
        ),
        colors = ListItemDefaults.segmentedColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = 0.5f
            )
        ),
        contentPadding = PaddingValues(
            all = 12.dp
        ),
        leadingContent = {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = RoundedCornerShape(percent = 50))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .padding(all = 10.dp)
            )
        },
        supportingContent = {
            if (item.detail != null) {
                Text(
                    text = stringResource(id = item.detail),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    ) {
        Text(
            text = stringResource(id = item.title),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun PreviewLazyPreferenceCategoryListItem() {
    LazyGOTheme {
        LazyPreferenceCategoryListItem(
            item = PreferenceCategoryListItem(
                title = R.string.label_appearance,
                detail = R.string.msg_appearance_preference_detail,
                icon = R.drawable.palette,
                route = LazyNavRoute.Settings.Appearance
            ),
            onClick = { }
        )
    }
}
