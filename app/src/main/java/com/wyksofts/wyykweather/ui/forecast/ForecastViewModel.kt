package com.wyksofts.wyykweather.ui.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wyksofts.wyykweather.model.citiesModel

class ForecastViewModel : ViewModel() {

    var date = ""
    var temperature = ""
    var weatherIcon = ""
    var min_temperature = ""
    var max_temperΩature = ""

    var liveData = MutableLiveData<ArrayList<forecastModel>>()

    var newlist = arrayListOf<forecastModel>()

    fun add(forecast: forecastModel){
        newlist.add(forecast)
        liveData.value = newlist
    }


}