package com.wyksofts.wyykweather.data

import android.util.Log
import android.widget.Toast
import org.json.JSONArray

class ForecastData {

    data class dateData(val dt_txt: String)
    data class tempData(val day: String, val min: String, val max: String)

    fun getWeatherIcon(json: String) : String{

        var icon = ""

        var jsonArray = JSONArray(json)
        for (jsonIndex in 0..(jsonArray.length() - 1)) {
            icon = jsonArray.getJSONObject(jsonIndex).getString("icon")
        }

        return icon
    }
}