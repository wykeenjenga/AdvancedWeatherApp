package com.wyksofts.wyykweather.ui.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.data.cloud.Favorite
import com.wyksofts.wyykweather.data.forecast.ForecastData
import com.wyksofts.wyykweather.model.FavoriteViewModel
import com.wyksofts.wyykweather.model.forecastModel
import com.wyksofts.wyykweather.ui.forecast.ForecastAdapter
import com.wyksofts.wyykweather.utils.*
import kotlinx.android.synthetic.main.fragment_detailed.*
import java.text.DateFormat
import java.util.*

class DetailedFragment : Fragment() {

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

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.currentIcon.observe(this, androidx.lifecycle.Observer  {
            favBtn.setImageResource(it)
        })

        val bundle = this.arguments
        if (bundle != null) {
            //get city details
            data_city = bundle.getString("city").toString()
            data_description = bundle.getString("description").toString()
            data_icon = bundle.getString("icon").toString()
            data_temperature = bundle.getString("temperature").toString()
            data_wind_speed = bundle.getString("wind_speed").toString()
            data_water_drop = bundle.getString("water_drop").toString()
            data_min = bundle.getString("min_temp").toString()
            data_max = bundle.getString("max_temp").toString()
            data_lat = bundle.getString("lat").toString()
            data_long = bundle.getString("long").toString()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_detailed, container, false)


        //set background colorScheme
        card_background.setBackgroundResource(BackgroundManager().getBackground(data_description))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            cardView.outlineAmbientShadowColor = IconManager().getColor(data_description)
            cardView.outlineSpotShadowColor = IconManager().getColor(data_description)
        }

        //variables
        initUI()

        return view
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

        context?.let {
            Glide.with(it)
                .load(IconManager().getIcon(data_icon))
                .into(weather_icon)
        }

        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        time.text = currentDateTimeString


        getWeatherForecast()


        arrow_back.setOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        //get favourite db data
        Favorite(viewModel).getCities(data_city)


        Animator().animate(favBtn,1.0f,1.2f,1100)
        weather_icon.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))

        //on favBtn clicked
        favBtn.setOnClickListener {
            if(viewModel.favIcon == R.drawable.baseline_favorite_24){
                viewModel.currentIcon.value =
                    context?.let { it1 -> Favorite(viewModel).deleteCity(data_city, it1) }
            }else{
                viewModel.currentIcon.value =
                    context?.let { it1 -> Favorite(viewModel).addCity(data_city, it1) }
            }
        }

        Animator().animate(imageViewBigCloud,0.5f,1.3f,10000)

    }



    //get weather forecast
    private fun getWeatherForecast(){

        progress_bar.isVisible = true

        //recyclerView
        detailed_city_recyclerview.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL ,false)

        val listdata = ArrayList<forecastModel>()

        val queue = Volley.newRequestQueue(context)
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

                val adapter = context?.let { ForecastAdapter(listdata, it) }
                detailed_city_recyclerview.adapter = adapter

                detailed_city_recyclerview.startAnimation(AnimationUtils.loadAnimation(context, R.anim.recycler_view_anim))

            }


        },{
            Toast.makeText(context, "error fetching forecast", Toast.LENGTH_LONG).show()
            progress_bar.isVisible = false
        })

        queue.add(jsonRequest)

    }

}