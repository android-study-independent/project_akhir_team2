package com.msib.growsmart.ui.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.response.PostRegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _postRegister = MutableLiveData<PostRegisterResponse>()
    val postRegister : LiveData<PostRegisterResponse> = _postRegister

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun postRegister(context: Context, name: String, answerQuestion: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postRegister(name, answerQuestion, email, password)
        client.enqueue(object : Callback<PostRegisterResponse>{
            override fun onResponse(
                call: Call<PostRegisterResponse>,
                response: Response<PostRegisterResponse>
            ) {
                if(response.isSuccessful) {
                    _postRegister.value = response.body()
                    _isLoading.value = false
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<PostRegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                    .show()
            }

        })
    }


}