package com.github.inlinefun.lazygo.composables.components.preferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.composables.screens.settings.PreferenceCategory
import com.github.inlinefun.lazygo.composables.screens.settings.PreferenceItem
import com.github.inlinefun.lazygo.data.preferences.LazyPreferences
import com.github.inlinefun.lazygo.data.preferences.PreferenceType
import com.github.inlinefun.lazygo.util.Constants
import com.github.inlinefun.lazygo.util.LazyGOTheme

@Composable
fun LazyPreferenceCategory(
    category: PreferenceCategory
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = category.title),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(
                    horizontal = Constants.Spacing.small,
                    vertical = Constants.Spacing.extraSmall
                )
        )
        category.items.forEachIndexed { index, item ->
            when(item.preference) {
                is PreferenceType.Choice -> {
                    LazyChoicePreference(
                        title = item.title,
                        detail = item.detail,
                        icon = item.icon,
                        preference = item.preference,
                        count = category.items.size,
                        index = index
                    )
                }
                is PreferenceType.Switch -> {
                    LazySwitchPreference(
                        title = item.title,
                        detail = item.detail,
                        icon = item.icon,
                        preference = item.preference,
                        count = category.items.size,
                        index = index
                    )
                }
                else -> {}
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLazyPreferenceCategory() {
    LazyGOTheme {
        Surface {
            LazyPreferenceCategory(
                category = PreferenceCategory(
                    title = R.string.label_map,
                    items = listOf(
                        PreferenceItem(
                            title = R.string.pref_label_map_theme,
                            icon = R.drawable.map,
                            preference = LazyPreferences.Appearance.mapTheme
                        ),
                        PreferenceItem(
                            title = R.string.pref_label_map_type,
                            icon = R.drawable.map,
                            preference = LazyPreferences.Appearance.mapType
                        )
                    )
                )
            )
        }
    }
}
