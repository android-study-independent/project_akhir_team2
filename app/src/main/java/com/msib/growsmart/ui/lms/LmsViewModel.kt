package com.msib.growsmart.ui.lms

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.GetLmsGroupResponse
import com.msib.growsmart.response.Moduls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LmsViewModel(
    private val preference: UserPreference
): ViewModel() {

    private val _getLmsGroup = MutableLiveData<Moduls>()
    val getLmsGroup: LiveData<Moduls> = _getLmsGroup

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun getLmsGroup(token: String, idForum: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getLmsGroup(token, idForum)
        client.enqueue(object : Callback<GetLmsGroupResponse> {
            override fun onResponse(
                call: Call<GetLmsGroupResponse>,
                response: Response<GetLmsGroupResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getLmsGroup.value = response.body()?.modul
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }

            }

            override fun onFailure(call: Call<GetLmsGroupResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    companion object{
        const val TAG = "LmsGuide"
    }

}