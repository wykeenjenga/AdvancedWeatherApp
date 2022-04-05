package com.wyksofts.wyykweather.ui.view;

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.data.forecast.ForecastData
import com.wyksofts.wyykweather.model.forecastModel
import com.wyksofts.wyykweather.ui.adapter.ForecastAdapter
import com.wyksofts.wyykweather.data.cloud.Favorite
import com.wyksofts.wyykweather.model.FavoriteViewModel
import com.wyksofts.wyykweather.utils.Constants
import com.wyksofts.wyykweather.utils.Convert
import com.wyksofts.wyykweather.utils.IconManager
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.DateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    //view_model
    lateinit var viewModel: FavoriteViewModel

    //shared resources
    var data_city: String = ""
    var data_description: String = ""
    var data_icon: String = ""
    var data_temperature: String = ""
    var data_wind_speed: String = ""
    var data_water_drop: String = ""
    var data_min: String = ""
    var data_max: String = ""
    var data_lat: String = ""
    var data_long: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.currentIcon.observe(this, androidx.lifecycle.Observer  {

            favBtn.setImageResource(it)
        })


        //get city details
        data_city = intent.getStringExtra("city").toString()
        data_description = intent.getStringExtra("description").toString()
        data_icon = intent.getStringExtra("icon").toString()
        data_temperature = intent.getStringExtra("temperature").toString()
        data_wind_speed = intent.getStringExtra("wind_speed").toString()
        data_water_drop = intent.getStringExtra("water_drop").toString()
        data_min = intent.getStringExtra("min_temp").toString()
        data_max = intent.getStringExtra("max_temp").toString()
        data_lat = intent.getStringExtra("lat").toString()
        data_long = intent.getStringExtra("long").toString()



        //variables
        initUI()

        setCardBackgroundColor(data_description)

    }

    //set background color of the app
    private fun setCardBackgroundColor(description: String) {
        card_background.setBackgroundResource(IconManager().getBackground(description))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cardView.outlineAmbientShadowColor = IconManager().getColor(description)
            cardView.outlineSpotShadowColor = IconManager().getColor(description)
        }

        val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        weather_icon.startAnimation(anim)
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {

        city.text = data_city
        status.text = data_description
        temperature.text = "$data_temperature\t°"
        water_drop.text = data_water_drop
        wind_speed.text = data_wind_speed
        min_temp.text = "Min:\t $data_min°"
        max_temp.text = "Max:\t $data_max°"

        Glide.with(applicationContext)
            .load(IconManager().getIcon(data_icon))
            .into(weather_icon)

        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        time.text = currentDateTimeString


        getWeatherForecast()


        arrow_back.setOnClickListener {
            this.finish()
        }

        //get favourite db data
        Favorite(viewModel).getCities(data_city)


        favBtn.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.zoom_in))

        //on favBtn clicked
        favBtn.setOnClickListener {
            if(viewModel.favIcon == R.drawable.baseline_favorite_24){
                viewModel.currentIcon.value = Favorite(viewModel).deleteCity(data_city, applicationContext)
            }else{
                viewModel.currentIcon.value = Favorite(viewModel).addCity(data_city,applicationContext)
            }
        }

    }



    //get weather forecast
    private fun getWeatherForecast(){

        progress_bar.isVisible = true

        //recyclerView
        val recyclerview = findViewById<RecyclerView>(R.id.detailed_city_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        val listdata = ArrayList<forecastModel>()

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/onecall?lat=$data_lat&lon=$data_long&exclude=current,minutely,hourly,alerts&appid=${Constants.OPEN_WEATHER_API_KEY}"


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
