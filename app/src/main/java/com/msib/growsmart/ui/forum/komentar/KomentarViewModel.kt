package com.msib.growsmart.ui.forum.komentar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.data.request.KirimKomentarRequest
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.GetKomentarResponse
import com.msib.growsmart.response.Komentars
import com.msib.growsmart.response.PostKomentarResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KomentarViewModel(
    private val preference: UserPreference
): ViewModel() {

    private val _postKomentar = MutableLiveData<PostKomentarResponse>()
    val postKomentar: LiveData<PostKomentarResponse> = _postKomentar

    private val _getKomentar = MutableLiveData<List<Komentars>>()
    val getKomentar: LiveData<List<Komentars>> = _getKomentar

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun getKomentar(token: String, idForum: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getKomentar(token, idForum)
        client.enqueue(object : Callback<GetKomentarResponse>{

            override fun onResponse(
                call: Call<GetKomentarResponse>,
                response: Response<GetKomentarResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getKomentar.value = response.body()?.komentars
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetKomentarResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }

        })

    }

    fun postKomentar(token: String, idForum: String, isi: String){
        _isLoading.value = true
        val komentarRequest = KirimKomentarRequest(isi)
        val client = ApiConfig.getApiService().postKomentar(token, idForum, komentarRequest)
        client.enqueue(object: Callback<PostKomentarResponse> {
            override fun onResponse(
                call: Call<PostKomentarResponse>,
                response: Response<PostKomentarResponse>
            ) {
                _isLoading.value = true
                if(response.isSuccessful) {
                    _postKomentar.value = response.body()
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostKomentarResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }

        })

    }

    companion object{
        const val TAG = "Komentar"
    }

}