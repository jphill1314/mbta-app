package com.jphill.mbtadepatureboard

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.location.LocationRequestCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

interface LocationCallbacks {
    fun onLocationFetched(latitude: Double, longitude: Double)
    fun onLocationFetchError()
    fun onMissingLocationPermission()
}

fun fetchUserLocation(
    context: Context,
    callbacks: LocationCallbacks,
) {
    val fineLocationGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val courseLocationGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ) == PackageManager.PERMISSION_GRANTED

    if (fineLocationGranted || courseLocationGranted) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.getCurrentLocation(
            LocationRequestCompat.QUALITY_BALANCED_POWER_ACCURACY,
            object : CancellationToken() {
                override fun isCancellationRequested() = false

                override fun onCanceledRequested(
                    p0: OnTokenCanceledListener
                ) = CancellationTokenSource().token
            },
        ).addOnSuccessListener { location ->
            if (location != null) {
                callbacks.onLocationFetched(
                    latitude = location.latitude,
                    longitude = location.longitude,
                )
            } else {
                callbacks.onLocationFetchError()
            }
        }.addOnFailureListener {
            callbacks.onLocationFetchError()
        }
    } else {
        callbacks.onMissingLocationPermission()
    }
}