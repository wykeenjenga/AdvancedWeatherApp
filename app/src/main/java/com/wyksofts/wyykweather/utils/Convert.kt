package com.wyksofts.wyykweather.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

class Convert {

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun convertDate(input: String) : String {

        val inFormat = SimpleDateFormat("dd.MM.yyyy")

        val date: Date = inFormat.parse(input)

        val outFormat = SimpleDateFormat("EEE")

        val day: String = outFormat.format(date)

        return day
    }

    fun convertTemp(temperature: String) : String {

        val temp =((((temperature).toFloat()-273.15)).toInt()).toString()

        return temp
    }



}