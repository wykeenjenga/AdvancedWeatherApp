package com.wyksofts.wyykweather.ui.view;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wyksofts.wyykweather.R

class DetailActivity : AppCompatActivity() {

    var city: String = ""
    var description: String = ""
    var icon: String = ""
    var temperature: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //get city name
        city = intent.getStringExtra("city").toString()
        description = intent.getStringExtra("description").toString()
        icon = intent.getStringExtra("icon").toString()
        temperature = intent.getStringExtra("temperature").toString()

        //variables


        initUI()
    }

    private fun initUI() {


    }


}