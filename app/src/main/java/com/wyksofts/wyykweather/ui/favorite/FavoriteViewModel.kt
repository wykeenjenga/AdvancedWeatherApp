package com.wyksofts.wyykweather.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.model.favoriteModel

class FavoriteViewModel : ViewModel() {

    var favIcon = R.drawable.baseline_favorite_border_24

    val currentIcon : MutableLiveData<Int> by lazy{
        MutableLiveData<Int>()
    }

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

    var liveData = MutableLiveData<ArrayList<favoriteModel>>()

    var newlist = arrayListOf<favoriteModel>()

    fun add(city: favoriteModel){
        newlist.add(city)
        liveData.value = newlist
    }

}

