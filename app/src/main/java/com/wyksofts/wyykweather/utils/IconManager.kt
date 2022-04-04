package com.wyksofts.wyykweather.utils

import android.graphics.Color
import com.wyksofts.wyykweather.R
import java.time.DayOfWeek

class IconManager {

    fun getIcon(weatherIcon: String?): Int {

        val icon: Int = when (weatherIcon) {
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

    //change background
    fun getBackground(background: String?): Int {

        val bg: Int = when (background) {

            "Clear", "Cloud" -> R.drawable.detailed_sunny_bg
            "Wind" -> R.drawable.detailed_wind_bg
            "Rain","Storm" -> R.drawable.detailed_rainny_bg
            "Snow", "Drizzle", "Thunderstorm" -> R.drawable.detailed_snow_bg
            else -> R.drawable.detailed_sunny_bg

        }
        return bg
    }

    fun getColor(colorBg: String?): Int {

        val color: Int = when (colorBg) {

            "MONDAY" -> Color.parseColor("#28E0AE")
            "TUESDAY" -> Color.parseColor("#FF0090")
            "WEDNESDAY" -> Color.parseColor("#FFAE00")
            "THURSDAY" -> Color.parseColor("#0090FF")
            "FRIDAY" -> Color.parseColor("#DC0000")
            "SATURDAY" -> Color.parseColor("#0051FF")
            "SUNDAY" -> Color.parseColor("#3D28E0")
            else -> Color.parseColor("#28E0AE")
        }

        return color
    }
}