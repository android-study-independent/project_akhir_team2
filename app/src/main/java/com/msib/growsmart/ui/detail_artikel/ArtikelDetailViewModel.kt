package com.msib.growsmart.ui.detail_artikel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.GetDetailArtikelResponse
import com.msib.growsmart.ui.forum.komentar.KomentarViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtikelDetailViewModel(
    private val preference: UserPreference
): ViewModel() {

    private val _getDetailArtikel = MutableLiveData<GetDetailArtikelResponse>()
    val getDetailArtikel: LiveData<GetDetailArtikelResponse> = _getDetailArtikel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun getDetailArtikel(token: String, idForum: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailArtikel(token, idForum)
        client.enqueue(object : Callback<GetDetailArtikelResponse> {

            override fun onResponse(
                call: Call<GetDetailArtikelResponse>,
                response: Response<GetDetailArtikelResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getDetailArtikel.value = response.body()
                }else {
                    Log.e(KomentarViewModel.TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetDetailArtikelResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(KomentarViewModel.TAG, "onFailure : ${t.message.toString()}")
            }

        })

    }


}