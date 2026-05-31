package com.github.inlinefun.lazygo.composables.settings.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.Constants
import com.github.inlinefun.lazygo.common.PreviewWrapper

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun SettingComponentWrapper(
    @StringRes
    title: Int,
    @StringRes
    description: Int? = null,
    @DrawableRes
    icon: Int? = null,
    trailingContent: @Composable () -> Unit = {},
    extraContent: @Composable () -> Unit = {},
    onClick: () -> Unit,
    index: Int = 0,
    count: Int = 1
) {
    SegmentedListItem(
        leadingContent = {
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
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
            }
        },
        supportingContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Constants.Spacing.small)
            ) {
                if (description != null) {
                    Text(
                        text = stringResource(id = description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                extraContent()
            }
        },
        trailingContent = trailingContent,
        onClick = onClick,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(
            all = Constants.Spacing.small
        ),
        shapes = ListItemDefaults.segmentedShapes(
            index = index,
            count = count
        ),
        colors = ListItemDefaults.segmentedColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun PreviewSettingComponentWrapper() {
    PreviewWrapper {
        SettingComponentWrapper(
            title = R.string.preference_label_amoled_theme,
            description = R.string.preference_description_amoled_theme,
            icon = R.drawable.dark_mode,
            onClick = {},
            trailingContent = {},
            extraContent = {},
            index = 0,
            count = 1
        )
    }
}
