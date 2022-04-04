package com.wyksofts.wyykweather.ui.view;

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.utils.Constants
import com.wyksofts.wyykweather.utils.IconManager
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DetailActivity : AppCompatActivity() {

    //shared resources
    var city: String = ""
    var description: String = ""
    var icon: String = ""
    var temperature: String = ""
    var wind_speed: String = ""
    var water_drop: String = ""
    var min: String = ""
    var max: String = ""

    //variables
    lateinit var cityT: TextView
    lateinit var statusT: TextView
    lateinit var temperatureT: TextView
    lateinit var weatherIcon: ImageView
    lateinit var arrow_back: ImageView
    lateinit var wind_speedT: TextView
    lateinit var water_dropT: TextView
    lateinit var time: TextView
    lateinit var mintemp: TextView
    lateinit var maxtemp: TextView
    lateinit var card_background: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //init variables
        cityT = findViewById(R.id.city)
        statusT = findViewById(R.id.status)
        temperatureT = findViewById(R.id.temperature)
        weatherIcon = findViewById(R.id.weather_icon)
        arrow_back = findViewById(R.id.arrow_back)
        wind_speedT = findViewById(R.id.wind_speed)
        water_dropT = findViewById(R.id.water_drop)
        time = findViewById(R.id.time)
        mintemp = findViewById(R.id.min_temp)
        maxtemp = findViewById(R.id.max_temp)

        card_background = findViewById(R.id.card_background)


        //get city name
        city = intent.getStringExtra("city").toString()
        description = intent.getStringExtra("description").toString()
        icon = intent.getStringExtra("icon").toString()
        temperature = intent.getStringExtra("temperature").toString()
        wind_speed = intent.getStringExtra("wind_speed").toString()
        water_drop = intent.getStringExtra("water_drop").toString()
        min = intent.getStringExtra("min_temp").toString()
        max = intent.getStringExtra("max_temp").toString()

        val lat = intent.getStringArrayExtra("lat").toString()
        val long = intent.getStringArrayExtra("long").toString()



        //variables
        initUI(lat,long)

        setCardBackgroundColor(description)
    }

    //set background color of the app
    private fun setCardBackgroundColor(description: String) {
        card_background.setBackgroundResource(IconManager().getBackground(description))
    }

    @SuppressLint("SetTextI18n")
    private fun initUI(lat: String, long: String) {
        cityT.text = city
        statusT.text = description
        temperatureT.text = "$temperature\t°C"
        water_dropT.text = water_drop
        wind_speedT.text = wind_speed
        mintemp.text = "Min:\t $min°C"
        maxtemp.text = "Max:\t $max°C"

        Glide.with(applicationContext)
            .load(IconManager().getIcon(icon))
            .into(weatherIcon)

        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        time.text = currentDateTimeString



        val unix = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val l = LocalDate.parse("04-04-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            l.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond

        } else {
            TODO("VERSION.SDK_INT < O")
        }

        getWeatherForecast(city,lat,long,unix)

    }

    //get weather forecast
    private fun getWeatherForecast(city: String, lat: String, long: String, unix: Long){

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=$city&dt=$unix&appid=${Constants.OPEN_WEATHER_API_KEY}"

        //days
        val days = arrayOf(1, 2, 3, 4, 5)

        for(day in days) {

            val jsonRequest = JsonObjectRequest(Request.Method.GET, url,null, { response ->

                //response is in the form of arraylist
                val list = response.getJSONArray("list").getJSONObject(day).getString("main")

                //create jsonObject
                var gson = Gson()
                var mMineUserEntity = gson?.fromJson(response, MineUserEntity.MineUserInfo::class.java)



                Toast.makeText(this, "Data:\t"+temperature, Toast.LENGTH_SHORT).show()

                Log.d("data---", list.toString())


            },{ Toast.makeText(this, "error fetching forecast", Toast.LENGTH_LONG).show() })

            queue.add(jsonRequest)

        }



    }



    @SuppressLint("SimpleDateFormat")
    private fun  getDate(date: Long): String {
        val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
        return timeFormatter.format(Date(date*1000L))
    }


}