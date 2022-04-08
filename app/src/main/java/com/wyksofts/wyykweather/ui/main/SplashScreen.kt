package com.wyksofts.wyykweather.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.databinding.FragmentSplashBinding


@SuppressLint("CustomSplashScreen")
class SplashScreen(val isSplash: Boolean) : Fragment(R.layout.fragment_splash) {

    private  var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!



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

        if (isSplash){

            binding.splashFragment.setBackgroundResource(R.drawable.splash_screen)
            binding.landingLayout.isVisible = false
            binding.splashView.isVisible = true

            binding.logo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))
            binding.title.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_down))

            val timer: Thread = object : Thread() {
                override fun run() {
                    try {
                        sleep(1000)

                        (activity as MainActivity?)?.showFragment(HomeFragment(), false)
                        //findNavController().graph.setStartDestination(R.id.dashboardFragment)
                        //findNavController().navigate(R.id.action_dashboardFragment_to_weatherDetailFragment)

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            timer.start()
        }else{
            showLandingScreen()
        }
    }

    private fun showLandingScreen() {

        //start animation when done show accounts view
        binding.splashFragment.setBackgroundResource(R.color.gray)
        binding.landingLayout.isVisible = true
        binding.splashView.isVisible = false


        binding.continueWithGoogle.setOnClickListener {
            binding.progressBar.isVisible = true
            (activity as MainActivity?)?.signIn(binding.progressBar)
        }

        binding.Continue.setOnClickListener{
            //navigate to dashboardFragment
            (activity as MainActivity?)?.showFragment(HomeFragment(), false)
        }


    }



}