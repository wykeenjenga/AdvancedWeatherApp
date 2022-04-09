package com.wyksofts.wyykweather.ui.currentWeather

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.api.WeatherApi
import com.wyksofts.wyykweather.utils.Convert
import com.wyksofts.wyykweather.utils.showToast

class CurrentWeather(
    private val viewModel: CurrentWeatherViewModel,
    private val currentLayout: LinearLayout,
    private val homeProgressBar: ProgressBar
) {

    fun showCurrentLocationData(context : Context, latitude: String, longitude: String){

        homeProgressBar.isVisible = true

        val queue = Volley.newRequestQueue(context)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, WeatherApi().getCurrentWeather(latitude,longitude),null, { response ->

                homeProgressBar.isVisible = false

                //show layout
                currentLayout.isVisible = true
                currentLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_down))

                //city
                viewModel.city = response.getString("name")
                viewModel.WeatherService.value = viewModel.city

                //description
                viewModel.description = response.getJSONArray("weather").getJSONObject(0).getString("main")
                viewModel.WeatherService.value = viewModel.description

                //icon
                viewModel.icon = response.getJSONArray("weather").getJSONObject(0).getString("icon")
                viewModel.WeatherService.value = viewModel.icon


                //temperature
                viewModel.temperature = Convert().convertTemp(response.getJSONObject("main").getString("temp"))
                viewModel.WeatherService.value = viewModel.temperature

                //water drop and wind speed
                viewModel.water_drop = response.getJSONObject("main").getString("humidity")+"\t%"
                viewModel.wind_speed = response.getJSONObject("wind").getString("speed")+"\tkm/h"
                viewModel.WeatherService.value = viewModel.water_drop
                viewModel.WeatherService.value = viewModel.wind_speed

                //get lat and long
                viewModel.lat = response.getJSONObject("coord").getString("lat").toString()
                viewModel.long = response.getJSONObject("coord").getString("lon").toString()

                //max and min temperature
                viewModel.min_temp = Convert().convertTemp(response.getJSONObject("main").getString("temp_min"))
                viewModel.max_temp = Convert().convertTemp(response.getJSONObject("main").getString("temp_max"))


            }, {
                homeProgressBar.isVisible = false
                showToast().showFailure(context,"Error: Slow connection...")
            })
        queue.add(jsonRequest)
    }


}