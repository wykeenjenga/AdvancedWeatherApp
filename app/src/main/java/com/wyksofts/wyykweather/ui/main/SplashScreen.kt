package com.wyksofts.wyykweather.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wyksofts.wyykweather.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //statusbar color
        window.statusBarColor= getResources().getColor(R.color.black)

        startMainActivity()
    }

    private fun startMainActivity() {

        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        timer.start()
    }
}