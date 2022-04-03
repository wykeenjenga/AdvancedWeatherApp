package com.wyksofts.wyykweather.model


data class citiesModel(
    val city: String, val temp: String,
    val icon: String, val description: String,
    val wind_speed: String, val water_drop: String,
    val mintemp: String, val maxtemp: String
)