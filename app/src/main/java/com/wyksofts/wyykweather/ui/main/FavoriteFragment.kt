package com.wyksofts.wyykweather.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.data.cloud.FavoriteDataModel
import com.wyksofts.wyykweather.databinding.FragmentFavoriteBinding
import com.wyksofts.wyykweather.model.favoriteModel
import com.wyksofts.wyykweather.ui.citiesWeather.cityDetailInterface
import com.wyksofts.wyykweather.ui.favorite.FavoriteAdapter
import com.wyksofts.wyykweather.ui.favorite.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(R.layout.fragment_favorite), cityDetailInterface {

    //view binding
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    //viewModel
    lateinit var viewModel: FavoriteViewModel


    //adapter
    var adapter : FavoriteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cities weather data
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.searchBtn.isActivated = false

        ViewCompat.setTransitionName(binding.title, "item_image")

        //get data from firebase
        context?.let { FavoriteDataModel(viewModel, it, binding.progressBar).getAllCities() }

        //status bar
        activity?.window?.statusBarColor= getResources().getColor(R.color.black)


        initUI()
        return binding.root
    }

    private fun initUI() {
        viewModel.liveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer  {

            binding.favrecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)

            adapter = FavoriteAdapter(this,viewModel.newlist, requireContext())
            binding.favrecyclerView.adapter = adapter

            binding.searchCity.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    filter(s.toString(), viewModel.newlist, binding.favrecyclerView, adapter!!)
                }
            })
        })

        binding.searchBtn.setOnClickListener{

            if(!searchViewLayout.isVisible){

                searchViewLayout.isVisible = true
                searchViewLayout.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down))
                favRecyclerViewLayout.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_down))

            }else{
                searchViewLayout.isVisible = false
                favRecyclerViewLayout.startAnimation(AnimationUtils.loadAnimation(context,R.anim.slide_up))
            }

        }


        //on onBackPressed
        binding.arrowBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

    }


    //init filter
    fun filter(city: String, data: ArrayList<favoriteModel>,
               recyclerView: RecyclerView, adapter: FavoriteAdapter){

        val arrayList: java.util.ArrayList<favoriteModel> = java.util.ArrayList<favoriteModel>()

        for (model in data) {
            if (model.city.toLowerCase().contains(city)) {
                recyclerView.setVisibility(View.VISIBLE)
                arrayList.add(model)
            } else {
                if (arrayList.isEmpty()) {
                    //binding.cancelSearch.isVisible = true
                    recyclerView.setVisibility(View.GONE)
                } else {
                    recyclerView.setVisibility(View.VISIBLE)
                }
            }
            adapter.upDateList(arrayList)
        }
    }

    override fun onItemClick(
        city: String, icon: String, description: String,
        temperature: String, wind_speed: String, water_drop: String,
        min_temp: String, max_temp: String, lat: String,
        long: String) {

        val fragment = DetailedFragment()
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

        fragment.arguments = bundle

        (activity as MainActivity?)?.showDetailedWeather(fragment,bundle,binding.title)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
