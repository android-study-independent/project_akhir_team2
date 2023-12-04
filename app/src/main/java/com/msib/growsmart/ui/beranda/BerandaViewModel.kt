package com.msib.growsmart.ui.beranda

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.ArticlesItem
import com.msib.growsmart.response.GetAllArticleResponse
import com.msib.growsmart.response.GetWeatherResponse
import com.msib.growsmart.ui.cuaca.CuacaViewModel
import com.msib.growsmart.utils.Constant.WEATHER_API_KEY
import com.msib.growsmart.utils.Constant.WEATHER_UNIT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaViewModel (
    private val preference: UserPreference
) : ViewModel() {
    private var _getWeather = MutableLiveData<GetWeatherResponse>()
    val getWeather: LiveData<GetWeatherResponse> = _getWeather

    private var _getArticle = MutableLiveData<List<ArticlesItem>>()
    val getArticle: LiveData<List<ArticlesItem>> = _getArticle

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun getWeather(lon: Double, lat: Double){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getWeather(WEATHER_API_KEY, lon, lat, WEATHER_UNIT)
        client.enqueue(object: Callback<GetWeatherResponse> {
            override fun onResponse(
                call: Call<GetWeatherResponse>,
                response: Response<GetWeatherResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getWeather.value = response.body()
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

    fun getAllArticle() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllArticle(WEATHER_API_KEY)
        client.enqueue(object: Callback<GetAllArticleResponse> {
            override fun onResponse(
                call: Call<GetAllArticleResponse>,
                response: Response<GetAllArticleResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getArticle.value = response.body()?.articles
                }else {
                    Log.e(CuacaViewModel.TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetAllArticleResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(CuacaViewModel.TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    companion object {
        const val TAG = "Beranda"
    }

}

