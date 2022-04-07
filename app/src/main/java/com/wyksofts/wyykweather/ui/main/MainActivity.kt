package com.wyksofts.wyykweather.ui.main;
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.databinding.FragmentDetailedBinding


class MainActivity : AppCompatActivity() {

    private val PERMISSION_ID = 13
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //show Splash screen
        showFragment(Splash())

        //check location permission
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        pref = this.getSharedPreferences("location", Context.MODE_PRIVATE)
        editor = pref?.edit()

        getLastLocation()
    }

    fun showDetailedWeather(fragment: Fragment, bundle: Bundle, homeTemperature: TextView?){
        val fm = supportFragmentManager.beginTransaction()
        fragment.arguments = bundle
        if (homeTemperature != null) {
            fm.add(R.id.root_fragment, fragment)
                .addSharedElement(homeTemperature,"image")
                .setCustomAnimations(R.anim.fade_out, R.anim.fade_in)
                .addToBackStack(null)
                .commit()
        }

    }

    fun showFragment(fragment: Fragment) {
        val fm = supportFragmentManager.beginTransaction()
        fm.add(R.id.root_fragment, fragment)
            .commit()
        //val appBarConfig = AppBarConfiguration(setOf(R.id.dashboardFragment))
        //findNavController(R.id.container_fragment).graph.setStartDestination(R.id.splashFragment)
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            CloseDialog().show(supportFragmentManager, "close_diag")
        }
    }



    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        //requestNewLocationData()
                        Toast.makeText(this, "Location not found", Toast.LENGTH_LONG).show()
                    } else {
                        //Toast.makeText(this, "${location.latitude}", Toast.LENGTH_LONG).show()
                        val latitude = location.latitude
                        val longitude = location.longitude

                        editor?.putString("latitude",latitude.toString())
                        editor?.putString("longitude",longitude.toString())
                        editor?.commit()

                    }
                }
            } else {
                Toast.makeText(this, "Please turn on your location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

}