package com.wyksofts.wyykweather.ui.main;
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wyksofts.wyykweather.R


class MainActivity : AppCompatActivity() {

    var closeDiag: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        closeDiag = Dialog(applicationContext)

        showFragment(Splash())
    }

    fun showFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootLayout, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            CloseDialog().show(supportFragmentManager, "close_diag")
        }
    }


}
