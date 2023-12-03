package com.msib.growsmart.ui.cuaca.perminggu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewFilterWeeklyWeatherBinding
import com.msib.growsmart.response.WeeklyWeatherItem
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar

class CuacaPermingguAdapter(private val listWeeklyWeather: List<WeeklyWeatherItem>) :
    RecyclerView.Adapter<CuacaPermingguAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewFilterWeeklyWeatherBinding.bind(view)

        fun bindItem(data: WeeklyWeatherItem){
            val degree = "℃"
            with(binding) {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val date = sdf.parse(data.date)
                val cal = Calendar.getInstance()
                cal.time = date
                val dayName = when (cal.get(Calendar.DAY_OF_WEEK)) {
                    1 -> "Min"
                    2 -> "Sen"
                    3 -> "Sel"
                    4 -> "Rab"
                    5 -> "Kam"
                    6 -> "Jum"
                    7 -> "Sab"
                    else -> "Hari Tidak Valid"
                }
                Picasso.get().load(data.weatherIcon).into(ivCuaca)
                tvHarian.text = dayName
                tvSuhu.text = data.temperature.toInt().toString()+degree
                tvCuacaDes.text = data.weatherDescription
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_filter_weekly_weather, parent, false)
        )
    }

    override fun getItemCount(): Int = listWeeklyWeather.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listWeeklyWeather[position])

    }

}