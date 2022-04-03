package com.wyksofts.wyykweather.ui.view;

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.utils.Constants
import com.wyksofts.wyykweather.utils.IconManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    //shared resources
    var city: String = ""
    var description: String = ""
    var icon: String = ""
    var temperature: String = ""
    var wind_speed: String = ""
    var water_drop: String = ""
    var min: String = ""
    var max: String = ""

    //variables
    lateinit var cityT: TextView
    lateinit var statusT: TextView
    lateinit var temperatureT: TextView
    lateinit var weatherIcon: ImageView
    lateinit var arrow_back: ImageView
    lateinit var wind_speedT: TextView
    lateinit var water_dropT: TextView
    lateinit var time: TextView
    lateinit var mintemp: TextView
    lateinit var maxtemp: TextView
    lateinit var card_background: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //init variables
        cityT = findViewById(R.id.city)
        statusT = findViewById(R.id.status)
        temperatureT = findViewById(R.id.temperature)
        weatherIcon = findViewById(R.id.weather_icon)
        arrow_back = findViewById(R.id.arrow_back)
        wind_speedT = findViewById(R.id.wind_speed)
        water_dropT = findViewById(R.id.water_drop)
        time = findViewById(R.id.time)
        mintemp = findViewById(R.id.min_temp)
        maxtemp = findViewById(R.id.max_temp)

        card_background = findViewById(R.id.card_background)


        //get city name
        city = intent.getStringExtra("city").toString()
        description = intent.getStringExtra("description").toString()
        icon = intent.getStringExtra("icon").toString()
        temperature = intent.getStringExtra("temperature").toString()
        wind_speed = intent.getStringExtra("wind_speed").toString()
        water_drop = intent.getStringExtra("water_drop").toString()
        min = intent.getStringExtra("min_temp").toString()
        max = intent.getStringExtra("max_temp").toString()

        val lat = intent.getStringArrayExtra("lat").toString()
        val long = intent.getStringArrayExtra("long").toString()

        getWeatherForecast(city,lat,long)

        //variables
        initUI()

        setCardBackgroundColor(description)
    }

    //set background color of the app
    private fun setCardBackgroundColor(description: String) {
        card_background.setBackgroundResource(IconManager().getBackground(description))
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        cityT.text = city
        statusT.text = description
        temperatureT.text = "$temperature\t°C"
        water_dropT.text = water_drop
        wind_speedT.text = wind_speed
        mintemp.text = "Min:\t $min°C"
        maxtemp.text = "Max:\t $max°C"

        Glide.with(applicationContext)
            .load(IconManager().getIcon(icon))
            .into(weatherIcon)

        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        time.text = currentDateTimeString


    }

    //get weather forecast
    private fun getWeatherForecast(city: String, lat: String, long: String){

        val queue = Volley.newRequestQueue(this)

        Toast.makeText(this, "data:\t${lat}\t,$long", Toast.LENGTH_SHORT).show()

        //val url = "api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${long}&appid=${Constants.OPEN_WEATHER_API_KEY}"

        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&appid=${Constants.OPEN_WEATHER_API_KEY}"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null, { response ->

                //temperature
//                var temperature = response.getJSONObject("main").getString("temp")
//                temperature=((((temperature).toFloat()-273.15)).toInt()).toString()

                var temperature = arrayOf(response.getJSONObject("main").getString("temp"))



                Toast.makeText(this, "data:\t"+temperature, Toast.LENGTH_SHORT).show()


                //add data
                //data.add(citiesModel(city, temperature, icon, description, waterDrop, windSpeed, mintemp, maxtemp))

                //val adapter = CityAdapter(data, applicationContext)
                //recyclerview.adapter = adapter


            },
            { Toast.makeText(this, "error fetching data5", Toast.LENGTH_LONG).show() })

        queue.add(jsonRequest)

    }



    @SuppressLint("SimpleDateFormat")
    private fun  getDate(date: Long): String {
        val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
        return timeFormatter.format(Date(date*1000L))
    }


}