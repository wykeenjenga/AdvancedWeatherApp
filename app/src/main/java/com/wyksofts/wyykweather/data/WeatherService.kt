package com.wyksofts.wyykweather.data

interface WeatherService {

    @GET("https://api.openweathermap.org/data/2.5/forecast?")
    fun getCurrentForecastData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("APPID") app_id: String?
    ): Call<WeatherResponse?>?
}

