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
import com.github.inlinefun.lazygo.util.LazyGOTheme

@Composable
fun LazyPreferenceItem(
    @StringRes
    title: Int,
    @StringRes
    detail: Int? = null,
    @DrawableRes
    icon: Int,
    index: Int = 0,
    count: Int = 1,
    onClick: () -> Unit,
    trailingContent: @Composable () -> Unit,
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
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = RoundedCornerShape(percent = 50))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .padding(all = 10.dp)
            )
        },
        trailingContent = trailingContent,
        supportingContent = {
            if (detail != null) {
                Text(
                    text = stringResource(id = detail),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun PreviewLazyPreferenceItem() {
    LazyGOTheme {
        LazyPreferenceItem(
            title = R.string.pref_label_app_theme,
            detail = null,
            icon = R.drawable.palette,
            onClick = {},
            trailingContent = {}
        )
    }
}
