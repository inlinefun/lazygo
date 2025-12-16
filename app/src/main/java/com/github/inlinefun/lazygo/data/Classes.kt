package com.github.inlinefun.lazygo.data

import androidx.compose.ui.graphics.vector.ImageVector
import com.github.inlinefun.lazygo.viewmodels.AppViewModel

data class PermissionStep(
    val titleId: Int,
    val descriptionId: Int,
    val icon: ImageVector,
    val buttonLabelId: Int,
    val action: AppViewModel.() -> Unit,
    val buttonState: AppViewModel.() -> Boolean
)

enum class RouteStatus {
    ACTIVE,
    PAUSED,
    INACTIVE;
}
