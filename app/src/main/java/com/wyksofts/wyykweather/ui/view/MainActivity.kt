package com.wyksofts.wyykweather.ui.view;
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
import com.wyksofts.wyykweather.ui.adapter.CityAdapter
import com.wyksofts.wyykweather.utils.Constants
import com.wyksofts.wyykweather.utils.IconManager
import java.lang.Math.ceil


class MainActivity : AppCompatActivity() {


    lateinit var city: TextView
    lateinit var status: TextView
    lateinit var temperature: TextView
    lateinit var wind_speed: TextView
    lateinit var water_drop: TextView
    lateinit var weather_icon: ImageView

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init variables
        city = findViewById(R.id.city)
        status = findViewById(R.id.status)
        temperature = findViewById(R.id.temperature)
        wind_speed = findViewById(R.id.wind_speed)
        water_drop = findViewById(R.id.water_drop)
        weather_icon = findViewById(R.id.weather_icon)

        //location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //window.statusBarColor= Color.parseColor("#1383C3")

        getCurrentWeather()

        getCitiesWeather()

    }


    //get cities weather
    private fun getCitiesWeather(){

        val cities = arrayOf("Nairobi", "Eden", "Elizabethtown", "New London", "Kampala", "Lagos")

        //adda data
        val recyclerview = findViewById<RecyclerView>(R.id.cities_recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<citiesModel>()

        var url = ""

        val queue = Volley.newRequestQueue(this)
        //loop through cities
        for (city in cities) {

            url = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${Constants.OPEN_WEATHER_API_KEY}"

            val jsonRequest = JsonObjectRequest(
                Request.Method.GET, url,null, { response ->

                    //city
                    val city = response.getString("name")

                    //description
                    val description = response.getJSONArray("weather").getJSONObject(0).getString("main")

                    //temperature
                    var temperature = response.getJSONObject("main").getString("temp")
                    temperature=((((temperature).toFloat()-273.15)).toInt()).toString()

                    //icon
                    val icon = response.getJSONArray("weather").getJSONObject(0).getString("icon")

                    //add data
                    data.add(citiesModel(city, temperature, icon))

                    val adapter = CityAdapter(data, applicationContext)
                    recyclerview.adapter = adapter

                },
                { Toast.makeText(this, "error fetching data", Toast.LENGTH_LONG).show() })

            queue.add(jsonRequest)
        }

    }

    //get device current location
    fun getCurrentWeather() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showPermissionAlert()
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener(this,
                OnSuccessListener<Location?> { location ->
                    // Got last known location.
                    if (location != null) {
                        //new showAppToast().showSuccess(getApplicationContext(),""+location.toString());
                        val latitude = location.latitude
                        val longitude = location.longitude

                        Toast.makeText(applicationContext,""+location?.latitude, Toast.LENGTH_LONG).show()
                        //get data using current location
                        val url = "https://api.openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&appid=${Constants.OPEN_WEATHER_API_KEY}"

                        showCurrentLocationData(url)

                    }
                    else{
                        Toast.makeText(applicationContext,"Error getting location", Toast.LENGTH_LONG).show()
                        val url = "https://api.openweathermap.org/data/2.5/weather?q=Nairobi&appid=${Constants.OPEN_WEATHER_API_KEY}"
                        showCurrentLocationData(url)
                    }
                })
    }


    @SuppressLint("SetTextI18n")
    private fun showCurrentLocationData(url: String){

        val queue = Volley.newRequestQueue(this)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null, { response ->

                city.text = response.getString("name")

                //description
                status.text = response.getJSONArray("weather").getJSONObject(0).getString("main")

                val icon = response.getJSONArray("weather").getJSONObject(0).getString("icon")

                Glide.with(this)
                    .load(IconManager().getIcon(icon))
                    .into(weather_icon)


                //temperature
                var tempr = response.getJSONObject("main").getString("temp")
                tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
                temperature.text="${tempr}°C"


                var mintemp=response.getJSONObject("main").getString("temp_min")
                mintemp=((((mintemp).toFloat()-273.15)).toInt()).toString()

                var maxtemp=response.getJSONObject("main").getString("temp_max")
                maxtemp=((ceil((maxtemp).toFloat()-273.15)).toInt()).toString()

                //pressure.text=response.getJSONObject("main").getString("pressure")
                water_drop.text = response.getJSONObject("main").getString("humidity")+"\t%"
                wind_speed.text = response.getJSONObject("wind").getString("speed")+"\tkm/h"


            }, {
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
            })
        queue.add(jsonRequest)

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            123 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    // permission was denied, show alert to explain permission
                    showPermissionAlert()
                } else {
                    //permission is granted now start a background service
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        //getLocation();
                    }
                }
            }
        }
    }

    //show location request
    private fun showPermissionAlert() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), 123
            )
        }
    }



}
