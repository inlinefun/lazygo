package com.github.inlinefun.lazygo.composables.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.components.special.ChoicePreferenceItem
import com.github.inlinefun.lazygo.components.special.PreferenceItem
import com.github.inlinefun.lazygo.components.special.SwitchPreferenceItem
import com.github.inlinefun.lazygo.preferences.Preference
import com.github.inlinefun.lazygo.preferences.PreferenceKey

data class PreferenceGroup<T>(
    @field:StringRes
    val label: Int,
    val items: List<PreferenceItem<T>>
) where T : Preference, T : PreferenceKey<*, *>

@Composable
fun <T> PreferenceGroup(
    group: PreferenceGroup<T>
) where T : Preference, T : PreferenceKey<*, *> {
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
                when(item.preference) {
                    is PreferenceKey.Switch -> SwitchPreferenceItem(
                        icon = item.icon,
                        title = item.label,
                        preference = item.preference,
                        count = group.items.size,
                        index = index
                    )
                    is PreferenceKey.Choice<*> -> ChoicePreferenceItem(
                        icon = item.icon,
                        title = item.label,
                        preference = item.preference,
                        count = group.items.size,
                        index = index
                    )
                    else -> {}
                }
            }
    }
}
