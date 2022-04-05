package com.wyksofts.wyykweather.ui.view;

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.data.ForecastData
import com.wyksofts.wyykweather.model.forecastModel
import com.wyksofts.wyykweather.ui.adapter.ForecastAdapter
import com.wyksofts.wyykweather.ui.helper.cloud.FavoriteCity
import com.wyksofts.wyykweather.utils.Constants
import com.wyksofts.wyykweather.utils.Convert
import com.wyksofts.wyykweather.utils.IconManager
import java.text.DateFormat
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
    var lat: String = ""
    var long: String = ""

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
    lateinit var cardView: CardView
    lateinit var progress_bar: ProgressBar
    lateinit var fav: ImageView



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
        cardView = findViewById(R.id.cardView)
        progress_bar = findViewById(R.id.progress_bar)
        fav = findViewById(R.id.like)

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
        lat = intent.getStringExtra("lat").toString()
        long = intent.getStringExtra("long").toString()



        //variables
        initUI()

        setCardBackgroundColor(description)
    }

    //set background color of the app
    private fun setCardBackgroundColor(description: String) {
        card_background.setBackgroundResource(IconManager().getBackground(description))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cardView.outlineAmbientShadowColor = IconManager().getColor(description)
            cardView.outlineSpotShadowColor = IconManager().getColor(description)
        }

        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        weatherIcon.startAnimation(anim)
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        cityT.text = city
        statusT.text = description
        temperatureT.text = "$temperature\t°"
        water_dropT.text = water_drop
        wind_speedT.text = wind_speed
        mintemp.text = "Min:\t $min°"
        maxtemp.text = "Max:\t $max°"

        Glide.with(applicationContext)
            .load(IconManager().getIcon(icon))
            .into(weatherIcon)

        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        time.text = currentDateTimeString


        getWeatherForecast()

        getCities()


        arrow_back.setOnClickListener {
            this.finish()
        }

        var isFavorite = false
        fav.setOnClickListener {

            FavoriteCity().addCity(city,applicationContext)
            fav.setImageResource(R.drawable.baseline_favorite_24)

//            //add city to favourite
//            if (isFavorite){
//                FavoriteCity().addCity(city,applicationContext)
//                fav.setImageResource(R.drawable.baseline_favorite_24)
//                isFavorite = true
//            }
//            else{
//                FavoriteCity().deleteCity(city,applicationContext)
//                fav.setImageResource(R.drawable.baseline_favorite_border_24)
//                isFavorite = false
//            }

        }


    }

    //get all cities
    private fun getCities(){

        val list = FavoriteCity().getCities(city,fav)

        //
    }

    //get weather forecast
    private fun getWeatherForecast(){

        progress_bar.isVisible = true

        //recyclerView
        val recyclerview = findViewById<RecyclerView>(R.id.detailed_city_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        val listdata = ArrayList<forecastModel>()

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$long&exclude=current,minutely,hourly,alerts&appid=${Constants.OPEN_WEATHER_API_KEY}"


        val jsonRequest = JsonObjectRequest(Request.Method.GET, url,null, { response ->

            progress_bar.isVisible = false

            //days
            val days = arrayOf(1, 2, 3, 4, 5, 6)


            for(day in days) {

                //create jsonObject
                val gson = Gson()

                val temp_list = response.getJSONArray("daily").getJSONObject(day).getString("temp")
                val day_list = response.getJSONArray("daily").getJSONObject(day).getString("dt").toLong()

                //icon
                val icon_list = response.getJSONArray("daily").getJSONObject(day).getJSONArray("weather")

                val weatherIcon = ForecastData().getWeatherIcon(icon_list.toString())

                //temperature
                val jsonString = temp_list
                val temp_model = gson.fromJson(jsonString, ForecastData.tempData::class.java)

                //temperature, min and max
                val temperature = Convert().convertTemp(temp_model.day)
                val min_temperature = Convert().convertTemp(temp_model.min)
                val max_temperature = Convert().convertTemp(temp_model.max)

                //date
                val date = Constants.getDate(day_list)

                //add data
                listdata.add(forecastModel(date,temperature,weatherIcon,min_temperature,max_temperature))

                val adapter = ForecastAdapter(listdata, applicationContext)
                recyclerview.adapter = adapter

            }


        },{
            Toast.makeText(this, "error fetching forecast", Toast.LENGTH_LONG).show()
            progress_bar.isVisible = false
        })

        queue.add(jsonRequest)

    }

}
