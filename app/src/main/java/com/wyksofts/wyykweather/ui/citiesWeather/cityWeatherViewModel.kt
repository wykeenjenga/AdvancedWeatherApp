package com.wyksofts.wyykweather.ui.citiesWeather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wyksofts.wyykweather.model.citiesModel

class cityWeatherViewModel : ViewModel() {

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

    var liveData = MutableLiveData<ArrayList<citiesModel>>()
    var newlist = arrayListOf<citiesModel>()

    fun add(city: citiesModel){
        newlist.add(city)
        liveData.value = newlist
    }


}