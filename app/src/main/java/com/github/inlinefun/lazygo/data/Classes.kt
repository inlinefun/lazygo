package com.github.inlinefun.lazygo.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.inlinefun.lazygo.R
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

enum class AppThemes(
    @get:StringRes
    override val resourceId: Int
): PreferenceEnum {
    DARK(
        resourceId = R.string.label_dark_mode
    ),
    LIGHT(
        resourceId = R.string.label_light_mode
    ),
    AUTO(
        resourceId = R.string.label_system_default
    );
}
