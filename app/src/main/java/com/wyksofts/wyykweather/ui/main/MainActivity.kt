package com.wyksofts.wyykweather.ui.main;
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wyksofts.wyykweather.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(Splash())

    }

    fun showFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootLayout, fragment)
            .commit()
    }


}
