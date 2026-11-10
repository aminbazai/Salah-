package com.salahgarden.app.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.CancellationSignal
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume

data class LocationFix(
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val country: String,
    val zoneId: java.time.ZoneId
)

class DeviceLocationProvider(private val context: Context) {

    fun hasPermission(): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

    suspend fun currentLocation(): LocationFix? {
        if (!hasPermission()) return null
        val location = findLocation() ?: return null
        val (name, country) = reverseGeocode(location)
        return LocationFix(
            latitude = location.latitude,
            longitude = location.longitude,
            name = name,
            country = country,
            zoneId = java.time.ZoneId.systemDefault()
        )
    }

    @SuppressLint("MissingPermission")
    private suspend fun findLocation(): Location? {
        val manager = context.getSystemService(LocationManager::class.java) ?: return null
        val providers = listOf(
            LocationManager.GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER
        ).filter { runCatching { manager.isProviderEnabled(it) }.getOrDefault(false) }
        if (providers.isEmpty()) return null

        val lastKnown = providers
            .mapNotNull { provider -> runCatching { manager.getLastKnownLocation(provider) }.getOrNull() }
            .maxByOrNull { it.time }
        if (lastKnown != null && System.currentTimeMillis() - lastKnown.time < 10 * 60 * 1000) {
            return lastKnown
        }

        val provider = providers.first()
        return withTimeoutOrNull(12_000L) {
            suspendCancellableCoroutine { continuation ->
                val handler = Handler(Looper.getMainLooper())
                val timeout = Runnable {
                    if (continuation.isActive) continuation.resume(lastKnown)
                }
                handler.postDelayed(timeout, 12_000L)

                val listener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        handler.removeCallbacks(timeout)
                        if (continuation.isActive) continuation.resume(location)
                    }

                    override fun onProviderEnabled(provider: String) = Unit
                    override fun onProviderDisabled(provider: String) = Unit
                    @Deprecated("Deprecated in API 29")
                    override fun onStatusChanged(provider: String?, status: Int, extras: android.os.Bundle?) = Unit
                }

                val cancellation = CancellationSignal()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    manager.getCurrentLocation(
                        provider,
                        cancellation,
                        context.mainExecutor
                    ) { location ->
                        handler.removeCallbacks(timeout)
                        if (continuation.isActive) continuation.resume(location ?: lastKnown)
                    }
                } else {
                    manager.requestSingleUpdate(provider, listener, Looper.getMainLooper())
                }

                continuation.invokeOnCancellation {
                    handler.removeCallbacks(timeout)
                    cancellation.cancel()
                    runCatching { manager.removeUpdates(listener) }
                }
            }
        }
    }

    private suspend fun reverseGeocode(location: Location): Pair<String, String> =
        withContext(Dispatchers.IO) {
            if (!Geocoder.isPresent()) return@withContext "My garden" to ""
            runCatching {
                val geocoder = Geocoder(context, Locale.getDefault())
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val address = addresses?.firstOrNull()
                val name = address?.locality
                    ?: address?.subAdminArea
                    ?: address?.adminArea
                    ?: "My garden"
                val country = address?.countryName ?: ""
                name to country
            }.getOrDefault("My garden" to "")
        }

    private suspend fun <T> withTimeoutOrNull(timeoutMs: Long, block: suspend () -> T?): T? =
        kotlinx.coroutines.withTimeoutOrNull(timeoutMs) { block() }
}
