package com.wyksofts.wyykweather.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.wyksofts.wyykweather.utils.Constants
import org.json.JSONException
import org.json.JSONObject

class NetworkManager {
}
class NetworkManager private constructor(context: Context) {
    var latitude = 0.0
    var longitude = 0.0
    private var requestQueue: RequestQueue?
    fun getRequestQueue(): RequestQueue {
        if (requestQueue == null) {
            requestQueue =
                Volley.newRequestQueue(NetworkManager.Companion.context.getApplicationContext())
        }
        return requestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>?) {
        getRequestQueue().add(req)
    }

    fun setLocation(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
    }

    private fun weatherService(): String {
        return Constants.BASE_URL_FORECAST+
                "lat=" + latitude +
                "&lon=" + longitude +
                "&appid=" + Constants.OPEN_WEATHER_API_KEY
    }

    fun GETWeather(
        okListener: NetworkListener<ArrayList<*>?>,
        errorListener: NetworkListener
    ) {
        val jsObjRequest = JsonObjectRequest(
            Request.Method.GET, weatherService(),
            null, { response ->
                try {
                    // Parse our json response and put it into a data structure of our choosing - at the moment just an arrayList
                    val result = parseWeatherObject(response)
                    okListener.onResult(result)
                } catch (e: JSONException) {
                    errorListener.onResult(null)
                    e.printStackTrace()
                }
            }) { errorListener.onResult(null) }
        addToRequestQueue(jsObjRequest)
    }

    @Throws(JSONException::class)
    private fun parseWeatherObject(json: JSONObject): ArrayList<*> {
        val arrayList: ArrayList<*> = ArrayList<Any?>()
        val list = json.getJSONArray("list")
        var i = 0
        while (i < list.length()) {
            val dtEntry = DateTimeEntry()
            val dtItem = list.getJSONObject(i)
            val dailyForecast = list.getJSONObject(i)
            val tempObject = dailyForecast.getJSONObject("main")
            val weatherArray = dtItem.getJSONArray("weather")
            val ob = weatherArray[0] as JSONObject
            dtEntry.date = dtItem.getString("dt_txt")
            dtEntry.condation = ob.getString("main")
            dtEntry.temperature = tempObject.getString("temp")
            dtEntry.icon = ob.getString("icon")
            arrayList.add(dtEntry)
            i += 8
        }
        return arrayList
    }

    companion object {
        private val instance: NetworkManager? = null
        private val context: Context? = null
        @Synchronized
        fun getInstance(context: Context?): NetworkManager {
            if (NetworkManager.Companion.instance == null) {
                NetworkManager.Companion.instance = NetworkManager(context)
            }
            return NetworkManager.Companion.instance
        }
    }

    init {
        NetworkManager.Companion.context = context
        requestQueue = getRequestQueue()
    }
}