package com.msib.growsmart.ui.cuaca

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.GetWeatherResponse
import com.msib.growsmart.response.HourlyWeatherItem
import com.msib.growsmart.ui.beranda.BerandaViewModel
import com.msib.growsmart.utils.Constant.WEATHER_UNIT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuacaViewModel(
    private val preference: UserPreference
) : ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    private var _getCurrentWeather = MutableLiveData<GetWeatherResponse>()
    val getCurrentWeather: LiveData<GetWeatherResponse> = _getCurrentWeather

    private var _getHourlyWeather = MutableLiveData<List<HourlyWeatherItem>>()
    val getHourlyWeather : LiveData<List<HourlyWeatherItem>> = _getHourlyWeather

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getCurrentWeather(token: String, lon: Double, lat: Double){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getWeather(token, lon, lat, WEATHER_UNIT)
        client.enqueue(object: Callback<GetWeatherResponse> {
            override fun onResponse(
                call: Call<GetWeatherResponse>,
                response: Response<GetWeatherResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getCurrentWeather.value = response.body()
                }else {
                    Log.e(BerandaViewModel.TAG, "onFailure : ${response.message()}")
                }

            }

            override fun onFailure(call: Call<GetWeatherResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(BerandaViewModel.TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    fun getListHourlyWeather(
        token: String,
        lon: Double,
        lat: Double
    ){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getWeather(token, lon, lat, WEATHER_UNIT)
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
        const val TAG = "Cuaca"
    }


}