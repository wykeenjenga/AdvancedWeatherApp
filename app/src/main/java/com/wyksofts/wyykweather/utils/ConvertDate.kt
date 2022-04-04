package com.wyksofts.wyykweather.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class ConvertDate {

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    fun convert(input: String) : String {

        val inFormat = SimpleDateFormat("dd.MM.yyyy")

        val date: Date = inFormat.parse(input)

        val outFormat = SimpleDateFormat("EEE")

        val day: String = outFormat.format(date)

        return day
    }
}