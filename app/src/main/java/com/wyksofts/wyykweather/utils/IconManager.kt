package com.wyksofts.wyykweather.utils

import android.graphics.Color
import com.wyksofts.wyykweather.R
import java.time.DayOfWeek

class IconManager {

    fun getforecastIcon(weatherIcon: String?): Int {

        val icon: Int = when (weatherIcon) {
            "01d" -> R.drawable.a01d_svg
            "01n" -> R.drawable.a01n_svg
            "09n" -> R.drawable.a09n_svg
            "09d" -> R.drawable.a09d_svg
            "10d" -> R.drawable.a10d_svg
            "10n" -> R.drawable.a10n_svg
            "11d" -> R.drawable.a11d_svg
            "11n" -> R.drawable.a11n_svg
            "02d" -> R.drawable.a02d_svg
            "02n" -> R.drawable.a02n_svg
            "03d" -> R.drawable.a03d_svg
            "03n" -> R.drawable.a03n_svg
            "04d" -> R.drawable.a04d_svg
            "04n" -> R.drawable.a04n_svg

            else -> R.drawable.a01n_svg
        }

        return icon
    }

    fun getIcon(weatherIcon: String?): Int {

        val icon: Int = when (weatherIcon) {
            "Clear", "Sands" -> R.drawable.cloud_sunny
            "Rain", "Snow", "Drizzle", "Thunderstorm" -> R.drawable.thunderstorm
            "Clouds" -> R.drawable.cloud_sunny
            "01d", "01n" -> R.drawable.sunny
            "09n", "09d", "10d", "10n" -> R.drawable.cloud_rain
            "11d", "11n" -> R.drawable.thunderstorm
            "02d", "02n", "03d", "03n", "04d", "04n" -> R.drawable.cloud_sunny
            else -> R.drawable.cloud_sunny
        }
        return icon
    }


    fun getColor(colorBg: String?): Int {

        val color: Int = when (colorBg) {

            "Mon" -> Color.parseColor("#28E0AE")
            "Tue" -> Color.parseColor("#FF0090")
            "Wed" -> Color.parseColor("#FFAE00")
            "Thu" -> Color.parseColor("#0090FF")
            "Fri" -> Color.parseColor("#DC0000")
            "Sat" -> Color.parseColor("#0051FF")
            "Sun" -> Color.parseColor("#3D28E0")
            "Rain" -> Color.parseColor("#0090FF")
            "Clouds" -> Color.parseColor("#0051FF")
            "Clear" -> Color.parseColor("#FFAE00")

            else -> Color.parseColor("#28E0AE")
        }
        return color
    }


}