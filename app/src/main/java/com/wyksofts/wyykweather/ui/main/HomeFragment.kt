package com.wyksofts.wyykweather.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.animation.AnimationUtils
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.databinding.FragmentHomeBinding
import com.wyksofts.wyykweather.databinding.FragmentHomeBinding.*
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.ui.citiesWeather.*
import com.wyksofts.wyykweather.ui.currentWeather.CurrentWeather
import com.wyksofts.wyykweather.ui.currentWeather.CurrentWeatherViewModel
import com.wyksofts.wyykweather.utils.BackgroundManager
import com.wyksofts.wyykweather.utils.IconManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.header_layout.view.*


class HomeFragment : Fragment(R.layout.fragment_home), cityDetailInterface {

    //model
    lateinit var currWViewModel: CurrentWeatherViewModel
    lateinit var viewModel: cityWeatherViewModel

    //view binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //location shared SharedPreferences
    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    //adapter
    var adapter : CityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
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

        initSearch()

        //get data for citiesModel
        getCitiesWeather()

        return binding.root
    }

    private fun getCitiesWeather() {

        // Cities weather data
        viewModel = ViewModelProvider(this).get(cityWeatherViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer  {

            binding.citiesRecylerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)

            adapter = CityAdapter(this,viewModel.newlist, requireContext())
            binding.citiesRecylerView.adapter = adapter

            binding.searchCity.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    filter(s.toString(), viewModel.newlist, binding.citiesRecylerView, adapter!!)
                }
            })
        })
    }

    @SuppressLint("RtlHardcoded")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val latitude = pref?.getString("latitude", "").toString()
        val longitude = pref?.getString("longitude", "").toString()

        //get weather data
        context?.let { CurrentWeather(currWViewModel,binding.currentLayout).showCurrentLocationData(it,latitude,longitude) }
        cityWeather(viewModel).showCitiesWeather(context)


        //navigation view
        val navDrawer: DrawerLayout = binding.drawerLayout
        val navigation = binding.navigationView

        //on menu clicked
        binding.menu.setOnClickListener {
            if(navDrawer.isDrawerVisible(Gravity.LEFT)){
                navDrawer.openDrawer(Gravity.RIGHT)
            }else{
                navDrawer.openDrawer(Gravity.LEFT)
            }
            navigation.header_image.setImageResource(IconManager().getIcon(currWViewModel.icon))
            navigation.header_text.text = currWViewModel.description
            navigation.header_bg.setBackgroundResource(BackgroundManager().getBackground(currWViewModel.description))

            onNavigationItemSelected(navDrawer, navigation)
        }


        homeCity.setOnClickListener {
            showCurrentLocationForecast()
        }

    }

    private fun showCurrentLocationForecast() {
        onItemClick(
            currWViewModel.city, currWViewModel.icon,
            currWViewModel.description, currWViewModel.temperature,
            currWViewModel.wind_speed, currWViewModel.water_drop, currWViewModel.min_temp,
            currWViewModel.max_temp, currWViewModel.lat, currWViewModel.long)
    }

    private fun onNavigationItemSelected(navDrawer: DrawerLayout, navigation: NavigationView){

        navigation.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.dashboardFragment -> {
                    if (navDrawer.isDrawerOpen(GravityCompat.START)) {
                        navDrawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }

                R.id.forecast ->{
                    showCurrentLocationForecast()
                    true
                }

                else -> {
                    if (navDrawer.isDrawerOpen(GravityCompat.START)) {
                        navDrawer.closeDrawer(GravityCompat.START)
                    }
                    false
                }
            }
        }

    }


    private fun initSearch() {

        binding.searchBtn.setOnClickListener{

            binding.currentLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up))
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

        (activity as MainActivity?)?.showDetailedWeather(detailedFragment,bundle,binding.homeTemperature)

    }


}