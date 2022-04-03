package com.wyksofts.wyykweather.ui.view;

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.utils.IconManager

class DetailActivity : AppCompatActivity() {

    //shared resources
    var city: String = ""
    var description: String = ""
    var icon: String = ""
    var temperature: String = ""

    //variables
    lateinit var cityT: TextView
    lateinit var statusT: TextView
    lateinit var temperatureT: TextView
    lateinit var weatherIcon: ImageView
    lateinit var arrow_back: ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //init variables
        cityT = findViewById(R.id.city)
        statusT = findViewById(R.id.status)
        temperatureT = findViewById(R.id.temperature)
        weatherIcon = findViewById(R.id.weather_icon)
        arrow_back = findViewById(R.id.arrow_back)

        //get city name
        city = intent.getStringExtra("city").toString()
        description = intent.getStringExtra("description").toString()
        icon = intent.getStringExtra("icon").toString()
        temperature = intent.getStringExtra("temperature").toString()

        //variables
        initUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        cityT.text = city
        statusT.text = description
        temperatureT.text = "$temperature\t°C"

        Glide.with(applicationContext)
            .load(IconManager().getIcon(icon))
            .into(weatherIcon)

    }


}