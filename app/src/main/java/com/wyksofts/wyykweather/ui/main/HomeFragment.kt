package com.wyksofts.wyykweather.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.databinding.FragmentHomeBinding
import com.wyksofts.wyykweather.databinding.FragmentHomeBinding.*
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.ui.citiesWeather.CitiesWeather
import com.wyksofts.wyykweather.ui.citiesWeather.CitiesWeatherViewModel
import com.wyksofts.wyykweather.ui.currentWeather.CurrentWeather
import com.wyksofts.wyykweather.ui.currentWeather.CurrentWeatherViewModel
import com.wyksofts.wyykweather.ui.citiesWeather.CityAdapter
import com.wyksofts.wyykweather.ui.citiesWeather.cityDetailInterface
import com.wyksofts.wyykweather.utils.IconManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home), cityDetailInterface {

    //model
    lateinit var currWViewModel: CurrentWeatherViewModel
    lateinit var citiesWeatherViewModel: CitiesWeatherViewModel

    //view binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //location shared SharedPreferences
    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)

        pref = context?.getSharedPreferences("location", Context.MODE_PRIVATE)
        editor = pref?.edit()


        //current weather data
        currWViewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
        currWViewModel.WeatherService.observe(this, androidx.lifecycle.Observer {

            //set data
            homeCity.text = currWViewModel.city

            Glide.with(this)
                .load(IconManager().getIcon(currWViewModel.icon))
                .into(homeWeatherIcon)

            homeStatus.text = currWViewModel.description

            homeTemperature.text = currWViewModel.temperature

            homeWater_Drop.text = currWViewModel.water_drop

            homeWind_Speed.text = currWViewModel.wind_speed

        })







    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        _binding = inflate(inflater, container, false)

        ViewCompat.setTransitionName(binding.homeTemperature, "item_image")

        val latitude = pref?.getString("latitude", "").toString()
        val longitude = pref?.getString("longitude", "").toString()

        context?.let { CurrentWeather(currWViewModel,binding.currentLayout).showCurrentLocationData(it,latitude,longitude) }


        //getCitiesWeather()

        initSearch()

        return binding.root
    }


    private fun initSearch() {

        binding.searchBtn.setOnClickListener{

            binding.currentLayout.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_up))
            binding.currentLayout.isVisible = false
            binding.searchView.isVisible = true
            binding.searchBtn.isVisible = false
            binding.cancelSearch.isVisible = true

            val anim = AnimationUtils.loadAnimation(context, R.anim.slide_down)
            binding.searchView.startAnimation(anim)
            binding.RecyclerViewLayout.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down))
        }

        binding.cancelSearch.setOnClickListener {

            binding.searchBtn.isVisible = true
            binding.cancelSearch.isVisible = false

            binding.currentLayout.isVisible = true
            binding.currentLayout.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down))
            binding.searchView.isVisible = false

            binding.RecyclerViewLayout.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_up))

            binding.searchCity.setText("")
        }
    }

    //init filter
    fun filter(
        city: String, data: ArrayList<citiesModel>,
        recyclerView: RecyclerView,
        adapter: CityAdapter){
        val arrayList: java.util.ArrayList<citiesModel> = java.util.ArrayList<citiesModel>()
        for (model in data) {
            if (model.city.toLowerCase().contains(city)) {
                recyclerView.setVisibility(View.VISIBLE)
                arrayList.add(model)
            } else {
                if (arrayList.isEmpty()) {
                    binding.cancelSearch.isVisible = true
                    recyclerView.setVisibility(View.GONE)
                } else {
                    recyclerView.setVisibility(View.VISIBLE)
                }
            }

            adapter.upDateList(arrayList)
        }
    }



    //get cities weather
    private fun getCitiesWeather() {

        val cities = arrayOf("Nairobi", "Eden", "Elizabethtown", "New London", "Kampala", "Lagos", "Dakar")

        //adda data

        binding.citiesRecylerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        //loop through cities
        for (city in cities) {

            CitiesWeather(citiesWeatherViewModel).showCitiesWeather(context,city)

        }

    }





    override fun onItemClick(city: String, icon: String, description: String, temperature: String,
                             wind_speed: String, water_drop: String, min_temp: String,
                             max_temp: String, lat: String, long: String) {

        val detailedFragment = DetailedFragment()
        val bundle = Bundle()
        bundle.putString("city", city)
        bundle.putString("description", description)
        bundle.putString("icon", icon)
        bundle.putString("temperature", temperature)
        bundle.putString("wind_speed", wind_speed)
        bundle.putString("water_drop", water_drop)
        bundle.putString("min_temp", min_temp)
        bundle.putString("max_temp", max_temp)
        bundle.putString("lat", lat)
        bundle.putString("long", long)
        detailedFragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.rootLayout, detailedFragment)
            .addToBackStack(null)
            .setCustomAnimations(R.anim.fade_in,
            R.anim.fade_out)
            .addSharedElement(binding.homeTemperature, "hero_image")
            .commit()
    }


}