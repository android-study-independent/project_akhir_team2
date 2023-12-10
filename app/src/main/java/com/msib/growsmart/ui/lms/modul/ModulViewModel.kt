package com.msib.growsmart.ui.lms.modul

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.GetLmsModuleResponse
import com.msib.growsmart.response.ModulItem
import com.msib.growsmart.ui.cuaca.perjam.CuacaPerjamViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModulViewModel(
    private val preference : UserPreference
): ViewModel() {

    private val _getLmsModul = MutableLiveData<List<ModulItem>>()
    val getLmsModul: LiveData<List<ModulItem>> = _getLmsModul

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun getLmsModul(token: String, groupId: String) {
        val client = ApiConfig.getApiService().getLmsModul(token, groupId)
        client.enqueue(object : Callback<GetLmsModuleResponse> {
            override fun onResponse(
                call: Call<GetLmsModuleResponse>,
                response: Response<GetLmsModuleResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getLmsModul.value = response.body()?.modul?.modul
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetLmsModuleResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(CuacaPerjamViewModel.TAG, "onFailure : ${t.message.toString()}")
            }

        })

    }

    companion object {
        const val TAG = "ModulLms"
    }

}