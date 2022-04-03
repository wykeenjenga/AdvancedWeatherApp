package com.wyksofts.wyykweather.ui.view;

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wyksofts.wyykweather.R
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


        //get city name
        city = intent.getStringExtra("city").toString()
        description = intent.getStringExtra("description").toString()
        icon = intent.getStringExtra("icon").toString()
        temperature = intent.getStringExtra("temperature").toString()
        wind_speed = intent.getStringExtra("wind_speed").toString()
        water_drop = intent.getStringExtra("water_drop").toString()
        min = intent.getStringExtra("min_temp").toString()
        max = intent.getStringExtra("max_temp").toString()

        //variables
        initUI()
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

    @SuppressLint("SimpleDateFormat")
    private fun  getDate(date: Long): String {
        val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
        return timeFormatter.format(Date(date*1000L))
    }


}