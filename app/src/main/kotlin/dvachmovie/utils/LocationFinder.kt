package dvachmovie.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import dvachmovie.architecture.logging.Logger

class LocationFinder constructor(context: Context,
                                 private val logger: Logger) {

    companion object {
        private const val TAG = "LocationFinder"
    }

    init {
        initLocationFinder(context)
        logger.d(TAG, "LocationFinder created")
    }

    private lateinit var locationManager: LocationManager
    /**
     * Sets up location service after permissions is granted
     */
    private fun initLocationFinder(context: Context) {
        if (
                ContextCompat.checkSelfPermission(context,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return
        }

        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        } catch (ex: RuntimeException) {
            logger.d(TAG, "Something error")
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLocation(context: Context, getLocation: (Location) -> Unit) {
        // Get GPS and network status
        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                logger.d(TAG, "Latitude is ${location.latitude}")
                logger.d(TAG, "Longitude is ${location.longitude}")

                getLocation(location)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }
        }

        when {
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ->
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
                        locationListener,
                        context.mainLooper)
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ->
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
                        locationListener,
                        context.mainLooper)
            else -> logger.d(TAG, "Cannot get location")
        }
    }
}
