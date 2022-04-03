package com.wyksofts.wyykweather.data

import android.telecom.Call
import com.wyksofts.wyykweather.model.WeatherResponse

interface WeatherService {

    @GET("https://api.openweathermap.org/data/2.5/forecast?")
    fun getCurrentForecastData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("APPID") app_id: String?
    ): Call<WeatherResponse?>?
}

