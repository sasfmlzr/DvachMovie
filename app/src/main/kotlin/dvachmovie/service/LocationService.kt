package dvachmovie.service

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.content.ContextCompat


class LocationService private constructor(context: Context,
                    private val locationListener: LocationListener) {


    private var locationManager: LocationManager? = null
    lateinit var location: Location
    var longitude: Double = 0.toDouble()
    var latitude: Double = 0.toDouble()
    private var isGPSEnabled: Boolean = false
    private var isNetworkEnabled: Boolean = false
    private var locationServiceAvailable: Boolean = false


    init {
        initLocationService(context)
        println("LocationService created")
    }

    /**
     * Sets up location service after permissions is granted
     */
    private fun initLocationService(context: Context) {
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
            longitude = 0.0
            latitude = 0.0
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Get GPS and network status
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isNetworkEnabled && !isGPSEnabled) {
                // cannot get location
                locationServiceAvailable = false
            } else {
                locationServiceAvailable = true

                if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), locationListener)
                    if (locationManager != null) {
                        location = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        updateCoordinates()
                    }
                }

                if (isGPSEnabled) {
                    locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), locationListener)

                    if (locationManager != null) {
                        location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        updateCoordinates()
                    }
                }
            }
        } catch (ex: Exception) {
            print("asd")
        }

    }

    private fun updateCoordinates() {
        println("wtf")
    }

    companion object {

        //The minimum distance to change updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        //The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES: Long = 0//1000 * 60 * 1; // 1 minute

        private var instance: LocationService? = null

        fun getLocationManager(context: Context, locationListener: LocationListener): LocationService {
            if (instance == null) {
                instance = LocationService(context, locationListener)
            }
            return instance as LocationService
        }
    }
}