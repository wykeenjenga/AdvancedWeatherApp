package com.wyksofts.wyykweather.utils

import com.wyksofts.wyykweather.R

class IconManager {

    fun getIcon(weatherIcon: String?): Int {

        val icon: Int

        icon = when (weatherIcon) {
            "Clear", "Sands" -> R.drawable.cloud_sunny
            "Rain", "Snow", "Drizzle", "Thunderstorm" -> R.drawable.thunderstorm
            "Clouds" -> R.drawable.cloud_sunny
            "01d", "01n" -> R.drawable.sunny
            "09n", "09d", "10d", "10n", "11d", "11n" -> R.drawable.cloud_rain
            "02d", "02n", "03d", "03n", "04d", "04n" -> R.drawable.cloud_sunny
            else -> R.drawable.cloud_sunny
        }
        return icon
    }
}