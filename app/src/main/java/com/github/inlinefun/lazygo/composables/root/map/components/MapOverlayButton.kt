package com.github.inlinefun.lazygo.composables.root.map.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.inlinefun.lazygo.common.Constants

@Composable
fun MapOverlayButton(
    @DrawableRes
    icon: Int,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    OutlinedIconButton(
        onClick = onClick,
        colors = IconButtonDefaults.outlinedIconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .size(48.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .padding(Constants.Spacing.small)
        )
    }
}
