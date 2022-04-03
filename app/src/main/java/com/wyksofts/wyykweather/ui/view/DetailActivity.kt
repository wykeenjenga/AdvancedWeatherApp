package com.wyksofts.wyykweather.ui.view;

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wyksofts.wyykweather.R

class DetailActivity : AppCompatActivity() {

    var city: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //get city name
        city = intent.getStringExtra("name").toString()
    }

}