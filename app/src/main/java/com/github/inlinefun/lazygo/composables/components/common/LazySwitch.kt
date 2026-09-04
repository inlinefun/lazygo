package com.github.inlinefun.lazygo.composables.components.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.util.Constants
import com.github.inlinefun.lazygo.util.LazyGOTheme

@Composable
fun LazySwitch(
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = onToggle,
        thumbContent = {
            AnimatedContent(
                targetState = if (checked) R.drawable.check else R.drawable.close
            ) { iconResource ->
                Icon(
                    painter = painterResource(id = iconResource),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(all = Constants.Spacing.extraSmall)
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewCheckedLazySwitch() {
    LazyGOTheme {
        LazySwitch(
            checked = true,
            onToggle = { }
        )
    }
}

@Preview
@Composable
private fun PreviewUncheckedLazySwitch() {
    LazyGOTheme {
        LazySwitch(
            checked = false,
            onToggle = { }
        )
    }
}
