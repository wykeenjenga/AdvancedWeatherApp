package com.wyksofts.wyykweather.ui.main.permission

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.wyksofts.wyykweather.ui.main.MainActivity
import com.wyksofts.wyykweather.utils.showToast

class getCurrentLocation(val activity: Activity) {

    //fused location
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    //SharedPreferences
    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    @SuppressLint("MissingPermission")
    fun getLastLocation() {

        //check location permission
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        //check location permission
        pref = activity.getSharedPreferences("location", Context.MODE_PRIVATE)
        editor = pref?.edit()

        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        //requestNewLocationData()
                        Toast.makeText(activity, "Location not found", Toast.LENGTH_LONG).show()
                    } else {
                        //Toast.makeText(activity, "${location.latitude}", Toast.LENGTH_LONG).show()
                        val latitude = location.latitude
                        val longitude = location.longitude

                        editor?.putString("latitude",latitude.toString())
                        editor?.putString("longitude",longitude.toString())
                        editor?.commit()

                    }
                }
            } else {
                Toast.makeText(activity, "Please turn on your location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                (activity as MainActivity?)?.startActivity(intent)
            }
        } else {
            (activity as MainActivity?)?.requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }


}