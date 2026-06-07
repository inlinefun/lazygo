package com.github.inlinefun.lazygo.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.location.provider.ProviderProperties
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// https://android.googlesource.com/platform/packages/apps/Settings/+/master/src/com/android/settings/SettingsActivity.java#109
private const val EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":settings:show_fragment_args"

// https://android.googlesource.com/platform/packages/apps/Settings/+/master/src/com/android/settings/SettingsActivity.java#114
private const val EXTRA_FRAGMENT_ARG_KEY = ":settings:fragment_args_key"

@HiltViewModel
class PermissionsViewModel @Inject constructor(
    // can not even use the name "application" because its internal in AndroidViewModel
    val context: Application
) : AndroidViewModel(
    application = context
) {

    private var _isMockLocationProvider = MutableStateFlow(false)
    val isMockLocationProvider = _isMockLocationProvider.asStateFlow()

    fun refreshMockLocationPermission() {
        _isMockLocationProvider.value = isMockLocationProvider()
    }

    fun isMockLocationProvider(): Boolean {
        return try {
            val locationManager = context
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                @SuppressLint("InlinedApi")
                locationManager.addTestProvider(
                    LocationManager.GPS_PROVIDER,
                    false, // requiresNetwork
                    false, // requiresSatellite
                    false, // requiresCell
                    false, // hasMonetaryCost
                    false, // supportsAltitude
                    false, // supportsSpeed
                    false, //supportsBearing
                    ProviderProperties.POWER_USAGE_LOW, // powerUsage
                    ProviderProperties.ACCURACY_COARSE // accuracy
                )
                true
            } catch (_: IllegalArgumentException) {
                true
            } catch (_: Exception) {
                Log.e(
                    "Permissions",
                    "Failed to spoof location, could indicate app not selected as a provider"
                )
                false
            } finally {
                locationManager
                    .removeTestProvider(LocationManager.GPS_PROVIDER)
            }
        } catch (_: Exception) {
            false
        }
    }

    @Throws(Exception::class)
    fun startActivityToSelectMockLocationProvider() {
        try {
            context.startActivity(
                Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
                    .apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                        val bundle = Bundle()
                            .apply {
                                putString(EXTRA_FRAGMENT_ARG_KEY, "mock_location_app")
                            }
                        putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, bundle)
                        putExtra(EXTRA_FRAGMENT_ARG_KEY, "mock_location_app")
                    }
            )
        } catch (_: ActivityNotFoundException) {
            context.startActivity(
                Intent(Settings.ACTION_DEVICE_INFO_SETTINGS)
                    .apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
            )
        } catch (e: Exception) {
            throw e
        }
    }

}
