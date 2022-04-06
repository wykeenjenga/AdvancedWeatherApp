package com.wyksofts.wyykweather.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.databinding.FragmentSplashBinding


class Splash : Fragment(R.layout.fragment_splash) {

    private  var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        //statusbar color
        activity?.window?.statusBarColor= getResources().getColor(R.color.black)

        startMainActivity()

        return binding.root
    }

    private fun startMainActivity() {

        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1000)
                    (activity as MainActivity?)?.showFragment(HomeFragment())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        timer.start()
    }


}