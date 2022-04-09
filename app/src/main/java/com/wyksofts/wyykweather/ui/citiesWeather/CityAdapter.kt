package com.wyksofts.wyykweather.ui.citiesWeather

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.utils.IconManager
import kotlin.collections.ArrayList


class CityAdapter(
    var onClickInteface: cityDetailInterface,
    var mList: List<citiesModel>,
    val viewModel: cityWeatherViewModel, val context: Context) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {


    //abstract var holder: ViewHolder

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the city_item_view view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = mList[position]

        //city
        holder.city.text = data.city
        holder.degreeText.text = "${data.temp}°C"

        val icon = data.icon

        Glide.with(context)
            .load(IconManager().getIcon(icon))
            .into(holder.weatherIcon)

        //on item clicked
        holder.card.setOnClickListener {

            //open detailed viewcity: String,
            onClickInteface.onItemClick(
                data.city, data.icon,
                data.description, data.temp,
                data.wind_speed, data.water_drop,
                data.mintemp, data.maxtemp,
                data.lat, data.long
            )
        }

        setIcons(data,holder)

    }

    private fun setIcons(data: citiesModel, holder: ViewHolder) {

        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val email = user?.email.toString()

        val docRef = db.collection("cities").document(email)

        docRef.get().addOnSuccessListener { document ->

            if (document != null) { //check if document is empty

                val cities = document.data //get data in in a list

                if (cities != null) {
                    for (cityName in cities.values) {
                        //sort data model of cities model.city.lowercase(Locale.getDefault()).contains(city)
                        if (data.city == cityName.toString()) {
                            holder.favIcon.setImageResource(R.drawable.baseline_favorite_24)
                        }
                    }
                }
            }
        }
    }

    // Clean all cities from recyclerview
    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        val arrayList: ArrayList<citiesModel> = ArrayList<citiesModel>()
        mList = arrayList
        arrayList.clear()
        notifyDataSetChanged()
    }

    // Add a list of items
    @SuppressLint("NotifyDataSetChanged")
    fun addAll(listData: List<citiesModel>?) {
        val arrayList: ArrayList<citiesModel> = ArrayList<citiesModel>()
        mList = arrayList
        arrayList.addAll(listData!!)
        notifyDataSetChanged()
    }


    //upDateList
    @SuppressLint("NotifyDataSetChanged")
    fun upDateList(listData: List<citiesModel>?) {
        val arrayList: ArrayList<citiesModel> = ArrayList<citiesModel>()
        mList = arrayList
        arrayList.addAll(listData!!)
        notifyDataSetChanged()
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }



    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon)
        val favIcon: ImageView = itemView.findViewById(R.id.favIcon)
        val city: TextView = itemView.findViewById(R.id.city)
        val degreeText: TextView = itemView.findViewById(R.id.degreeText)
        val card: RelativeLayout = itemView.findViewById(R.id.itemView)
    }
}

