package com.wyksofts.wyykweather.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.model.citiesModel
import com.wyksofts.wyykweather.ui.view.DetailActivity
import com.wyksofts.wyykweather.utils.IconManager

class CityAdapter(private var mList: List<citiesModel>,
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

        holder.itemView.setOnClickListener {

            Toast.makeText(context,""+data.city, Toast.LENGTH_SHORT).show()
            //open detailed view


        }

    }

    //upDateList
    fun upDateList(listData: List<citiesModel>?) {
        val arrayList: ArrayList<citiesModel> = ArrayList<citiesModel>()
        mList = arrayList
        arrayList.addAll(listData!!)
        notifyDataSetChanged()
    }

    //open detailed fragment
    private fun  openDetailedView(city: String) {
        val intent: Intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("name", city)
        context?.startActivity(intent)
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
    }
}

