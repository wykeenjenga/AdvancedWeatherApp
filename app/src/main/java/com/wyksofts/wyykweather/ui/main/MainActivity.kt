package com.wyksofts.wyykweather.ui.main;
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.ui.cities.CityAdapter
import com.wyksofts.wyykweather.utils.Constants
import com.wyksofts.wyykweather.utils.IconManager
import java.lang.Math.ceil


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showHomePage()

    }

    fun showHomePage() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootLayout, home())
            .commit()
    }


}
