package com.github.inlinefun.lazygo.composables

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.data.BooleanPreferenceKey
import com.github.inlinefun.lazygo.data.EnumPreferenceKey
import com.github.inlinefun.lazygo.data.PreferenceEnum
import com.github.inlinefun.lazygo.data.PreferencesStore
import com.github.inlinefun.lazygo.data.UserPreferences
import com.github.inlinefun.lazygo.ui.Constants

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingsScreen(
    preferencesStore: PreferencesStore
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.label_settings)
                    )
                },
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(Constants.Padding.small)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    @Suppress("deprecation")
                                    (context as? Activity)?.onBackPressed()
                                }
                            )
                    )
                },
                windowInsets = WindowInsets()
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        contentWindowInsets = WindowInsets(),
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(
                count = UserPreferences.all.size
            ) { index ->
                val category = UserPreferences.all[index]
                Text(
                    text = stringResource(category.label),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(Constants.Padding.small)
                )
                category.preferences.forEach { preferenceKey ->
                    when(preferenceKey) {
                        is BooleanPreferenceKey -> BooleanPreferenceComponent(preferenceKey, preferencesStore)
                        is EnumPreferenceKey -> EnumPreferenceComponent(
                            preferenceKey = @Suppress("unchecked_cast") (preferenceKey as EnumPreferenceKey<PreferenceEnum>),
                            preferencesStore = preferencesStore
                        )
                    }
                }
            }
        }
    }
}
