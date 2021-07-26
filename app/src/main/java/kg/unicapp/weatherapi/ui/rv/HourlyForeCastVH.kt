package kg.unicapp.weatherapi.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.unicapp.weatherapi.R
import kg.unicapp.weatherapi.format
import kg.unicapp.weatherapi.models.Constants
import kg.unicapp.weatherapi.models.HourlyForeCast
import kotlin.math.roundToInt

class HourlyForeCastVH(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(item: HourlyForeCast){
        val tv_precipitation = itemView.findViewById<TextView>(R.id.tv_precipitation)
        val tv_time = itemView.findViewById<TextView>(R.id.tv_time)
        val tv_temp = itemView.findViewById<TextView>(R.id.tv_temp)
        val iv_weather_icon = itemView.findViewById<ImageView>(R.id.iv_weather_icon)

        itemView.run {
            tv_time.text = item.date.format("HH:mm")
            item.probability?.let{
                tv_precipitation.text = "${(it * 100).roundToInt()} %"
            }
            tv_temp.text = item.temp?.roundToInt().toString()

            Glide.with(itemView.context)
                .load("${Constants.iconUri}${item.weather?.get(0)?.icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
    }
    companion object {
        fun create(parent: ViewGroup): HourlyForeCastVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly_forecast, parent, false)
            return HourlyForeCastVH(view)
        }
    }
}