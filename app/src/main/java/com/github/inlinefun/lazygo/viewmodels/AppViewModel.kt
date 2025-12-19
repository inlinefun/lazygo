package com.github.inlinefun.lazygo.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.provider.ProviderProperties
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.github.inlinefun.lazygo.MainApplication
import com.github.inlinefun.lazygo.data.PreferencesStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel(
    private val context: Application
): AndroidViewModel(application = context) {

    private val _isLocationProvider = MutableStateFlow(false)
    val isLocationProvider = _isLocationProvider.asStateFlow()
    val preferences: PreferencesStore = (context as MainApplication).preferencesStore

    fun hasNecessaryPermissions(): Boolean {
        return canAccessFineLocation()
                && isLocationProvider()
    }

    fun canAccessFineLocation(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("InlinedApi")
    fun isLocationProvider(): Boolean {
        /*
         * There probably is a better way than a try catch in a try catch
         */
        return try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                locationManager.addTestProvider(
                    LocationManager.GPS_PROVIDER,
                    false,
                    false,
                    false,
                    false,
                    false,
                    true,
                    true,
                    ProviderProperties.POWER_USAGE_LOW,
                    ProviderProperties.ACCURACY_FINE
                )
                true
            } catch (_: IllegalArgumentException) {
                true
            } catch (_: Exception) {
                Log.d("yes", "Failed to get provider")
                false
            } finally {
                locationManager.removeTestProvider(LocationManager.GPS_PROVIDER)
            }
        } catch (_: Exception) {
            false
        }
    }

    fun refreshLocationProviderStatus() {
        _isLocationProvider.value = isLocationProvider()
    }

    fun setLocationProvider() {
        try {
            context.startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        } catch (_: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Settings.ACTION_SETTINGS
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }

}