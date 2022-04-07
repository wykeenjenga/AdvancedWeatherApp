package com.wyksofts.wyykweather.utils

import com.wyksofts.wyykweather.R

class BackgroundManager {

    //change background
    fun getBackground(background: String?): Int {

        val bg: Int = when (background) {

            "Sunny", "Clear" -> R.drawable.detailed_sunny_bg
            "Clouds" -> R.drawable.detailed_clouds_bg
            "Wind" -> R.drawable.detailed_wind_bg
            "Rain","Storm" -> R.drawable.detailed_rainny_bg
            "Snow", "Drizzle", "Thunderstorm" -> R.drawable.detailed_snow_bg
            else -> R.drawable.detailed_default_bg
        }
        return bg
    }
}