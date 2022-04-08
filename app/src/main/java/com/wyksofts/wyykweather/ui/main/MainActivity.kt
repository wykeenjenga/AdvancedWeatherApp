package com.wyksofts.wyykweather.ui.main;
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.ui.forecast.ForecastViewModel
import com.wyksofts.wyykweather.ui.main.permission.getCurrentLocation
import com.wyksofts.wyykweather.ui.user.ContinueWithGoogle
import com.wyksofts.wyykweather.ui.user.UserViewModel
import com.wyksofts.wyykweather.utils.showToast


class MainActivity : AppCompatActivity() {

    //google signing
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 191
    var progressBar: ProgressBar? = null

    //location
    private val PERMISSION_ID = 13


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //show Splash screen
        showFragment(SplashScreen(true), false)

        //get current location
        getCurrentLocation(this).getLastLocation()

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

    fun showFragment(fragment: Fragment, b: Boolean) {

        if(b){
            val fm = supportFragmentManager.beginTransaction()
                .addToBackStack(null)
            fm.add(R.id.root_fragment, fragment)
                .commit()

        }else{
            val fm = supportFragmentManager.beginTransaction()
            fm.add(R.id.root_fragment, fragment)
                .commit()
        }
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            CloseDialog().show(supportFragmentManager, "close_diag")
        }
    }


    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getCurrentLocation(this).getLastLocation()

                //show Splash screen
                showFragment(SplashScreen(true), false)
            }
        }
    }



    //signing with google account
    fun signIn(proBar: ProgressBar) {

        this.progressBar = proBar

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            this.progressBar?.isVisible = false
            ContinueWithGoogle(this).handleSignInResult(task,applicationContext)
        }
    }

}