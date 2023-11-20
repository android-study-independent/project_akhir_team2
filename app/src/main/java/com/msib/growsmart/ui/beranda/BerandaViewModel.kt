package com.msib.growsmart.ui.beranda

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.GetWeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaViewModel (
    private val preference: UserPreference
) : ViewModel() {
    private var _getWeather = MutableLiveData<GetWeatherResponse>()
    val getWeather: LiveData<GetWeatherResponse> = _getWeather

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun getWeather(lon: Double, lat: Double){
        val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxNiwiaWF0IjoxNzAwMDI3MjA1fQ.jGI0_QbGbNLBsHYObv4yCm418sovX2LGKz1AwUpY68A"
        val client = ApiConfig.getApiService().getWeather(apiKey, lon, lat, "metric" )
        client.enqueue(object: Callback<GetWeatherResponse> {
            override fun onResponse(
                call: Call<GetWeatherResponse>,
                response: Response<GetWeatherResponse>
            ) {
                if(response.isSuccessful) {
                    _getWeather.value = response.body()
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }

            }

            override fun onFailure(call: Call<GetWeatherResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    companion object {
        const val TAG = "Beranda"
    }

}

