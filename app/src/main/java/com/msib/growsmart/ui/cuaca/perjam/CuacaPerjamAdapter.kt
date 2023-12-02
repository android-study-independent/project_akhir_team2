package com.msib.growsmart.ui.cuaca.perjam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewFilterHourlyWeatherBinding
import com.msib.growsmart.response.HourlyWeatherItem
import com.squareup.picasso.Picasso

class CuacaPerjamAdapter(
    private val listHourlyWeather: List<HourlyWeatherItem>) : RecyclerView.Adapter<CuacaPerjamAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewFilterHourlyWeatherBinding.bind(view)

        fun bindItem(data: HourlyWeatherItem){
            val degree = "℃"
            with(binding) {
                Picasso.get().load(data.weatherIcon).into(ivCuaca)
                tvJam.text = data.time
                tvSuhu.text = data.temperature.toInt().toString()+degree
                tvPeluangHujan.text = data.pop.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_filter_hourly_weather, parent, false)
        )
    }

    override fun getItemCount(): Int = listHourlyWeather.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listHourlyWeather[position])

    }


}