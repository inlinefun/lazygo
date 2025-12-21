package com.github.inlinefun.lazygo.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.data.API
import com.github.inlinefun.lazygo.ui.Constants
import com.github.inlinefun.lazygo.viewmodels.AppViewModel

@Composable
fun SplashScreen(
    viewModel: AppViewModel,
    navigateToSetupScreen: () -> Unit,
    navigateToMapScreen: () -> Unit
) {
    val splashes = arrayOf(
        R.string.splash_label_one,
        R.string.splash_label_two,
        R.string.splash_label_three,
        R.string.splash_label_four,
        R.string.splash_label_five
    )
    val currentSplashResId = splashes.random()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        API.init(context)
        val hasPerms = viewModel.hasNecessaryPermissions()
        if (hasPerms) {
            navigateToMapScreen()
        } else {
            navigateToSetupScreen()
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = Constants.Spacing.medium
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.Padding.medium)
    ) {
        Spacer(
            modifier = Modifier
                .weight(1.0f)
        )
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "logo",
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
        )
        Spacer(
            modifier = Modifier
                .weight(1.0f)
        )
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(),
            strokeCap = StrokeCap.Round
        )
        Text(
            text = stringResource(currentSplashResId),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
