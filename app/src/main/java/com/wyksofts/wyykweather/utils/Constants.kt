package com.wyksofts.wyykweather.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val BASE_URL_FORECAST = "https://api.openweathermap.org/data/2.5/forecast?"
    const val OPEN_WEATHER_API_KEY = "d9abb2c1d05c5882e937cffd1ecd4923"

    @SuppressLint("SimpleDateFormat")
    public fun  getDate(date: Long): String {
        val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
        return timeFormatter.format(Date(date*1000L))
    }


    //cities list
    val CITIES = arrayOf("Eden", "Elizabethtown", "New London", "Kampala", "Lagos", "Dakar", "Nairobi")

}