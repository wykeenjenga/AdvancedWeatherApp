package com.wyksofts.wyykweather.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.model.forecastModel
import com.wyksofts.wyykweather.ui.view.DetailActivity
import com.wyksofts.wyykweather.utils.IconManager

class CityAdapter(
    var mList: List<citiesModel>,
    val context: Context) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the city_item_view view
        // that is used to hold list item
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

            //Toast.makeText(context, "${data.lat},${data.long}", Toast.LENGTH_SHORT).show()

            //open detailed viewcity: String,
            openDetailedView(
                data.city,data.icon,
                data.description,data.temp,
                data.wind_speed,data.water_drop,
                data.mintemp,data.maxtemp,
                data.lat, data.long
            )


        }

    }

    //upDateList
    @SuppressLint("NotifyDataSetChanged")
    fun upDateList(listData: List<citiesModel>?) {
        val arrayList: ArrayList<citiesModel> = ArrayList<citiesModel>()
        mList = arrayList
        arrayList.addAll(listData!!)
        notifyDataSetChanged()
    }

    //open detailed fragment
    private fun  openDetailedView(
        city: String,
        icon: String,
        description: String,
        temperature: String,
        wind_speed: String,
        water_drop: String,
        min_temp: String,
        max_temp: String,
        lat: String,
        long: String
    ) {
        val intent: Intent = Intent(context, DetailActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("city", city)
        intent.putExtra("description", description)
        intent.putExtra("icon", icon)
        intent.putExtra("temperature", temperature)
        intent.putExtra("wind_speed", wind_speed)
        intent.putExtra("water_drop", water_drop)
        intent.putExtra("min_temp", min_temp)
        intent.putExtra("max_temp", max_temp)
        intent.putExtra("lat", lat)
        intent.putExtra("long", long)
        context.startActivity(intent)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon)
        val city: TextView = itemView.findViewById(R.id.city)
        val degreeText: TextView = itemView.findViewById(R.id.degreeText)
        val card: RelativeLayout = itemView.findViewById(R.id.itemView)
    }
}

