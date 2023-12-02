package com.msib.growsmart.ui.cuaca.perjam

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.response.CurrentWeather
import com.msib.growsmart.response.GetWeatherResponse
import com.msib.growsmart.response.HourlyWeatherItem
import com.msib.growsmart.ui.beranda.BerandaViewModel
import com.msib.growsmart.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuacaPerjamViewModel: ViewModel() {

    private var _getHourlyWeather = MutableLiveData<List<HourlyWeatherItem>>()
    val getHourlyWeather : LiveData<List<HourlyWeatherItem>> = _getHourlyWeather

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getListHourlyWeather(
        lon: Double,
        lat: Double
    ){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getWeather(
            Constant.WEATHER_API_KEY, lon, lat,
            Constant.WEATHER_UNIT
        )
        client.enqueue(object : Callback<GetWeatherResponse> {
            override fun onResponse(
                call: Call<GetWeatherResponse>,
                response: Response<GetWeatherResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getHourlyWeather.value = response.body()?.hourlyWeather
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetWeatherResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val TAG = "CuacaPerJam"
    }


}