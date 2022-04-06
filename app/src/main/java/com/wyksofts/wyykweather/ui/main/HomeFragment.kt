package com.wyksofts.wyykweather.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.databinding.FragmentDetailedBinding
import com.wyksofts.wyykweather.databinding.FragmentHomeBinding
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.ui.cities.CityAdapter
import com.wyksofts.wyykweather.ui.cities.cityDetailInterface
import com.wyksofts.wyykweather.utils.Constants
import com.wyksofts.wyykweather.utils.Convert
import com.wyksofts.wyykweather.utils.IconManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.search_toolbar.*

@Suppress("NAME_SHADOWING")
class HomeFragment : Fragment(R.layout.fragment_home), cityDetailInterface {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        ViewCompat.setTransitionName(binding.homeTemperature, "item_image")

        showCurrentLocationData()

        getCitiesWeather()

        initSearch()

        return binding.root
    }

    private fun initSearch() {

        binding.searchBtn.setOnClickListener{
            binding.searchView.isVisible = true
            binding.searchBtn.isVisible = false

            val anim = AnimationUtils.loadAnimation(context, R.anim.slide_down)
            binding.searchView.startAnimation(anim)

        }
    }

    //init filter
    fun filter(
        city: String, data: ArrayList<citiesModel>,
        recyclerView: RecyclerView,
        adapter: CityAdapter
    ) {
        val arrayList: java.util.ArrayList<citiesModel> = java.util.ArrayList<citiesModel>()
        for (model in data) {
            if (model.city.toLowerCase().contains(city)) {
                recyclerView.setVisibility(View.VISIBLE)
                arrayList.add(model)
            } else {
                if (arrayList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE)
                } else {
                    recyclerView.setVisibility(View.VISIBLE)
                }
            }
            if (city.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE)
            }
            adapter.upDateList(arrayList)
        }
    }



    //get cities weather
    private fun getCitiesWeather() {

        val cities = arrayOf("Nairobi", "Eden", "Elizabethtown", "New London", "Kampala", "Lagos", "Dakar")

        //adda data

        binding.citiesRecylerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val data = ArrayList<citiesModel>()

        var url = ""

        val queue = Volley.newRequestQueue(context)
        //loop through cities
        for (city in cities) {

            url = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${Constants.OPEN_WEATHER_API_KEY}"

            val jsonRequest = JsonObjectRequest(
                Request.Method.GET, url,null, { response ->

                    //city
                    val city = response.getString("name")

                    //description
                    val description = response.getJSONArray("weather").getJSONObject(0).getString("main")

                    //icon
                    val icon = response.getJSONArray("weather").getJSONObject(0).getString("icon")

                    //wind_speed and water_drop
                    val waterDrop = response.getJSONObject("main").getString("humidity")+"\t%"
                    val windSpeed = response.getJSONObject("wind").getString("speed")+"\tkm/h"

                    //get lat and long
                    val lat = response.getJSONObject("coord").getString("lat").toString()
                    val long = response.getJSONObject("coord").getString("lon").toString()

                    //temperature
                    var temperature = response.getJSONObject("main").getString("temp")
                    temperature=((((temperature).toFloat()-273.15)).toInt()).toString()


                    //max and min temperature
                    var mintemp=response.getJSONObject("main").getString("temp_min")
                    mintemp=((((mintemp).toFloat()-273.15)).toInt()).toString()

                    var maxtemp=response.getJSONObject("main").getString("temp_max")
                    maxtemp=((Math.ceil((maxtemp).toFloat()-273.15)).toInt()).toString()

                    //add data
                    data.add(
                        citiesModel(city, temperature, icon, description,
                        waterDrop, windSpeed, mintemp, maxtemp, lat, long)
                    )

                    val adapter = CityAdapter(this,data, requireContext())
                    binding.citiesRecylerView.adapter = adapter

                    binding.searchCity.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                        override fun afterTextChanged(s: Editable) {
                             filter(s.toString(), data, binding.citiesRecylerView, adapter)
                        }
                    })

                },
                { Toast.makeText(context, "error fetching data", Toast.LENGTH_LONG).show() })

            queue.add(jsonRequest)
        }

    }



    @SuppressLint("SetTextI18n")
    private fun showCurrentLocationData(){

        val url = "https://api.openweathermap.org/data/2.5/weather?q=Nairobi&appid=${Constants.OPEN_WEATHER_API_KEY}"

        val queue = Volley.newRequestQueue(context)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null, { response ->

                homeCity.text = response.getString("name")

                //description
                homeStatus.text = response.getJSONArray("weather").getJSONObject(0).getString("main")

                val icon = response.getJSONArray("weather").getJSONObject(0).getString("icon")

                Glide.with(this)
                    .load(IconManager().getIcon(icon))
                    .into(homeWeatherIcon)


                //temperature
                val temp = Convert().convertTemp(response.getJSONObject("main").getString("temp"))
                homeTemperature.text="${temp}°"


                //pressure.text=response.getJSONObject("main").getString("pressure")
                homeWater_Drop.text = response.getJSONObject("main").getString("humidity")+"\t%"
                homeWind_Speed.text = response.getJSONObject("wind").getString("speed")+"\tkm/h"


            }, {
                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
            })
        queue.add(jsonRequest)
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