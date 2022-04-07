package com.wyksofts.wyykweather.data.cloud

import androidx.core.view.isVisible
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wyksofts.wyykweather.ui.favorite.favoriteCitiesWeather

class FavoriteCityList {

    val db = Firebase.firestore

    //get all cities
    fun getAllCities(){

        var sortData: ArrayList<String>? = null

        db.collection("cities")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){

                    val cityName = document.getString("cityName")

                    val cities = listOf(cityName)

                    sortData?.add(cities.toString())
                }
            }
            .addOnFailureListener { exception ->

            }
    }
}