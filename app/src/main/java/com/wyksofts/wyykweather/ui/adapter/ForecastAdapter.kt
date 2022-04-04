package com.wyksofts.wyykweather.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.model.forecastModel

class ForecastAdapter(var mList: List<forecastModel>,
                      val context: Context) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = mList[position]

        //city
        holder.day.text = data.dayOfTheWeek
        holder.temperature.text = "${data.temperature}°"

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val day: TextView = itemView.findViewById(R.id.day)
        val temperature: TextView = itemView.findViewById(R.id.temperature)
    }



}