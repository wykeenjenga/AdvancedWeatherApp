package com.wyksofts.wyykweather.ui.forecast

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.data.forecast.ForecastData
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.utils.Constants
import com.wyksofts.wyykweather.utils.Convert

class forecastWeather(val viewModel: ForecastViewModel, val context: Context) {

    fun getForecast(progressBar: ProgressBar, data_lat: String, data_long: String){

        progressBar.isVisible = true

        val queue = Volley.newRequestQueue(context)
        val url = "https://api.openweathermap.org/data/2.5/onecall?lat=$data_lat&lon=$data_long&exclude=current,minutely,hourly,alerts&appid=${Constants.OPEN_WEATHER_API_KEY}"


        val jsonRequest = JsonObjectRequest(Request.Method.GET, url,null, { response ->

            progressBar.isVisible = false

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

                val model = forecastModel(date,temperature,weatherIcon,min_temperature,max_temperature)

                viewModel.add(model)
            }


        },{
            Toast.makeText(context, "error fetching forecast", Toast.LENGTH_LONG).show()
            progressBar.isVisible = false
        })

        queue.add(jsonRequest)

    }

}