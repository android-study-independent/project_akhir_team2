package com.msib.growsmart.ui.resetPassword

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msib.growsmart.data.request.NewPasswordRequest
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.response.PutNewPasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPasswordViewModel : ViewModel() {
    private val _putNewPass = MutableLiveData<PutNewPasswordResponse>()
    val putNewPass: LiveData<PutNewPasswordResponse> = _putNewPass

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun putNewPass(
        context: Context,
        email: String,
        newPass: String
    ){
        _isLoading.value = true
        val newPass = NewPasswordRequest(email, newPass)
        val client = ApiConfig.getApiService().putNewPassword(newPass)
        client.enqueue(object: Callback<PutNewPasswordResponse> {
            override fun onResponse(
                call: Call<PutNewPasswordResponse>,
                response: Response<PutNewPasswordResponse>
            ) {
                if (response.isSuccessful) {
                    _putNewPass.value = response.body()
                    _isLoading.value= false
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<PutNewPasswordResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

}