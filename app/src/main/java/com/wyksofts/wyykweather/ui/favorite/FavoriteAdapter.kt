package com.wyksofts.wyykweather.ui.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.model.favoriteModel
import com.wyksofts.wyykweather.ui.citiesWeather.cityDetailInterface
import com.wyksofts.wyykweather.utils.IconManager

class FavoriteAdapter(
    var onClickInteface: cityDetailInterface,
    var mList: List<favoriteModel>,
    val context: Context) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {


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
            onClickInteface.onItemClick(
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
    fun upDateList(listData: List<favoriteModel>?) {
        val arrayList: ArrayList<favoriteModel> = ArrayList<favoriteModel>()
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
        val city: TextView = itemView.findViewById(R.id.city)
        val degreeText: TextView = itemView.findViewById(R.id.degreeText)
        val card: RelativeLayout = itemView.findViewById(R.id.itemView)
    }
}
