package com.msib.growsmart.ui.cuaca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewHourlyWeatherItemBinding
import com.msib.growsmart.response.HourlyWeatherItem
import com.squareup.picasso.Picasso

class CuacaAdapter(
    private val context: Context,
    private val listHourlyWeather: List<HourlyWeatherItem>,
    ): RecyclerView.Adapter<CuacaAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewHourlyWeatherItemBinding.bind(view)

        fun bindItem(data: HourlyWeatherItem){
            with(binding) {
                Picasso.get().load(data.weatherIcon).into(ivWeather)
                tvJam.text = data.time
                tvSuhu.text = data.temperature.toInt().toString()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_hourly_weather_item, parent, false)
        )
    }

    override fun getItemCount(): Int = listHourlyWeather.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listHourlyWeather[position])
    }

}