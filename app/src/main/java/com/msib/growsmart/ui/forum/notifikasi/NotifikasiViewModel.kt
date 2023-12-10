package com.msib.growsmart.ui.forum.notifikasi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.FormattedForumsItem
import com.msib.growsmart.response.GetNotificationResponse
import com.msib.growsmart.ui.cuaca.perjam.CuacaPerjamViewModel
import com.msib.growsmart.ui.lms.modul.ModulViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifikasiViewModel(
    private val preference: UserPreference
) : ViewModel() {
    private var _getNotifikasi = MutableLiveData<List<FormattedForumsItem>>()
    val getNotifikasi: LiveData<List<FormattedForumsItem>> = _getNotifikasi

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun getNotification(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNotifikasi(token)
        client.enqueue(object : Callback<GetNotificationResponse>{
            override fun onResponse(
                call: Call<GetNotificationResponse>,
                response: Response<GetNotificationResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getNotifikasi.value = response.body()?.formattedForums
                }else {
                    Log.e(ModulViewModel.TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetNotificationResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(CuacaPerjamViewModel.TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    companion object {
        const val TAG = "Notifikasi"
    }
}