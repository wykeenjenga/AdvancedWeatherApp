package com.wyksofts.wyykweather.ui.citiesWeather

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wyksofts.wyykweather.api.WeatherApi
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.utils.Constants
import com.wyksofts.wyykweather.utils.Convert

class cityWeather(private val viewModel: cityWeatherViewModel) {


    fun showCitiesWeather(context: Context?){

        val cities = Constants.CITIES

        //loop through cities
        for (city in cities) {

            val queue = Volley.newRequestQueue(context)
            val jsonRequest = JsonObjectRequest(
                Request.Method.GET, WeatherApi().getCitiesWeather(city) ,null, { response ->

                    //city
                    viewModel.city = response.getString("name")

                    //description
                    viewModel.description = response.getJSONArray("weather").getJSONObject(0).getString("main")

                    //icon
                    viewModel.icon = response.getJSONArray("weather").getJSONObject(0).getString("icon")

                    //temperature
                    viewModel.temperature = Convert().convertTemp(response.getJSONObject("main").getString("temp"))

                    //water drop and wind speed
                    viewModel.water_drop = response.getJSONObject("main").getString("humidity")+"\t%"
                    viewModel.wind_speed = response.getJSONObject("wind").getString("speed")+"\tkm/h"


                    //get lat and long
                    viewModel.lat = response.getJSONObject("coord").getString("lat").toString()
                    viewModel.long = response.getJSONObject("coord").getString("lon").toString()


                    //max and min temperature
                    viewModel.min_temp = Convert().convertTemp(response.getJSONObject("main").getString("temp_min"))
                    viewModel.max_temp = Convert().convertTemp(response.getJSONObject("main").getString("temp_max"))


                    val model = citiesModel(
                        viewModel.isCityFav, viewModel.city, viewModel.temperature,
                        viewModel.icon, viewModel.description,
                        viewModel.wind_speed, viewModel.water_drop,
                        viewModel.min_temp, viewModel.max_temp,
                        viewModel.lat, viewModel.long
                    )

                    viewModel.add(model)


                }, {
                    //Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
                })
            queue.add(jsonRequest)

        }

    }



}