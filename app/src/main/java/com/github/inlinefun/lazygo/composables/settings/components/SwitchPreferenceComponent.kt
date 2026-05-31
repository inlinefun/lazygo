package com.github.inlinefun.lazygo.composables.settings.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.preferences.PreferenceKey
import com.github.inlinefun.lazygo.preferences.getPreferenceAsState
import com.github.inlinefun.lazygo.preferences.setPreference
import kotlinx.coroutines.launch

@Composable
fun SwitchPreferenceComponent(
    key: PreferenceKey.Switch,
    index: Int = 0,
    count: Int = 1
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val value by context
        .getPreferenceAsState(key)
    SettingComponentWrapper(
        title = key.label,
        description = key.description,
        icon = key.icon,
        count = count,
        index = index,
        onClick = {
            scope.launch {
                context
                    .setPreference(key, !value)
            }
        },
        trailingContent = {
            Switch(
                checked = value,
                onCheckedChange = null,
                thumbContent = {
                    AnimatedContent(
                        targetState = if (value) R.drawable.check else R.drawable.close
                    ) { icon ->
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                }
            )
        }
    )
}
