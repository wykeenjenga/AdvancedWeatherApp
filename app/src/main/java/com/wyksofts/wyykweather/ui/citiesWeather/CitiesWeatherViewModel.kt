package com.wyksofts.wyykweather.ui.citiesWeather

import androidx.lifecycle.MutableLiveData

class CitiesWeatherViewModel {

    var city = ""
    var icon = ""
    var description = ""
    var temperature = ""
    var wind_speed = ""
    var water_drop = ""
    var min_temp = ""
    var max_temp = ""
    var lat = ""
    var long = ""

    val CitiesWeatherService : MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
}