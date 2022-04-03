import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wyksofts.wyykweather.R
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter() {
// : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

//    var forecastList = mutableListOf<ForecastItemViewModel>()
//
//    fun addForecast(list : List<ForecastItemViewModel>){
//        forecastList.clear()
//        forecastList.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item_view, parent, false)
//        return ForecastViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
//        forecastList[position].let {
//            holder.bind(forecastElement = it)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return forecastList.size
//    }
//
//    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        fun bind(forecastElement : ForecastItemViewModel) {
//
//            itemView.findViewById<TextView>(R.id.degreeText).text = "${forecastElement.degreeDay} °C ${forecastElement.description}"
//            itemView.findViewById<TextView>(R.id.status).text = "${forecastElement.cityName}"
//            Glide.with(itemView.context)
//                    .load("http://openweathermap.org/img/w/${forecastElement.icon}.png")
//                    .into(itemView.findViewById(R.id.weatherIcon))
//
//            itemView.setOnClickListener { openDetailsView(itemView.context, forecastElement) }
//        }
//

//
//        private fun  getDate(date: Long): String {
//            val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
//            return timeFormatter.format(Date(date*1000L))
//        }
//
//    }

}