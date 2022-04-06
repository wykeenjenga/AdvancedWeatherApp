package com.wyksofts.wyykweather.ui.citiesWeather

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.wyksofts.wyykweather.api.WeatherApi
import com.wyksofts.wyykweather.utils.Convert

class CitiesWeather(private val viewModel: CitiesWeatherViewModel) {


    fun showCitiesWeather(context: Context?, cities: String){

        val queue = Volley.newRequestQueue(context)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, WeatherApi().getCitiesWeather(cities),null, { response ->

                //city
                viewModel.city = response.getString("name")
                viewModel.CitiesWeatherService.value = viewModel.city

                //description
                viewModel.description = response.getJSONArray("weather").getJSONObject(0).getString("main")
                viewModel.CitiesWeatherService.value = viewModel.description

                //icon
                viewModel.icon = response.getJSONArray("weather").getJSONObject(0).getString("icon")
                viewModel.CitiesWeatherService.value = viewModel.icon


                //temperature
                viewModel.temperature = Convert().convertTemp(response.getJSONObject("main").getString("temp"))
                viewModel.CitiesWeatherService.value = viewModel.temperature

                //water drop and wind speed
                viewModel.water_drop = response.getJSONObject("main").getString("humidity")+"\t%"
                viewModel.wind_speed = response.getJSONObject("wind").getString("speed")+"\tkm/h"
                viewModel.CitiesWeatherService.value = viewModel.water_drop
                viewModel.CitiesWeatherService.value = viewModel.wind_speed


                //get lat and long
                viewModel.lat = response.getJSONObject("coord").getString("lat").toString()
                viewModel.long = response.getJSONObject("coord").getString("lon").toString()
                viewModel.CitiesWeatherService.value = viewModel.lat
                viewModel.CitiesWeatherService.value = viewModel.long


                //max and min temperature
                viewModel.min_temp = Convert().convertTemp(response.getJSONObject("main").getString("temp_min"))
                viewModel.max_temp = Convert().convertTemp(response.getJSONObject("main").getString("temp_max"))
                viewModel.CitiesWeatherService.value = viewModel.min_temp
                viewModel.CitiesWeatherService.value = viewModel.max_temp

            }, {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
            })
        queue.add(jsonRequest)
    }

}