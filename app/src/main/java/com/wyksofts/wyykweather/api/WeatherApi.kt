package com.wyksofts.wyykweather.api

import com.wyksofts.wyykweather.utils.Constants

class WeatherApi {

    fun getCurrentWeather(latitude: String, longitude: String) : String {

        var api = ""

        if (latitude.equals(null)|| longitude.equals(null)){
            api = "${Constants.BASE_URL}weather?q=Nairobi&appid=${Constants.OPEN_WEATHER_API_KEY}"
        }else{
            api = "${Constants.BASE_URL}weather?lat=$latitude&lon=$longitude&appid=${Constants.OPEN_WEATHER_API_KEY}"
        }

        return api
    }


    fun getCitiesWeather(city: String): String {
        return "${Constants.BASE_URL}weather?q=$city&appid=${Constants.OPEN_WEATHER_API_KEY}"
    }


}