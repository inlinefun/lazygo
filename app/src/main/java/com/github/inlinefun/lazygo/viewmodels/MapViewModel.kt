package com.github.inlinefun.lazygo.viewmodels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class MapViewModel(
    context: Application
): AndroidViewModel(
    application = context
) {

    fun startTrackingLocation(
        context: Context
    ) {

    }

    fun getLocation(
        context: Context,
        onSuccess: (Location) -> Unit,
        onError: (Exception) -> Unit
    ) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val client = LocationServices.getFusedLocationProviderClient(context)
            client.lastLocation
                .addOnSuccessListener { cached ->
                    cached?.let {
                        onSuccess(it)
                    } ?:
                    client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
                        .addOnSuccessListener(onSuccess)
                        .addOnFailureListener(onError)
                }
        } else {
            onError(IllegalStateException("Failed to access Location"))
        }
    }

}