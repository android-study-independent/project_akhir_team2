package com.msib.growsmart.ui.forgetPassword

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msib.growsmart.data.request.ForgetPasswordRequest
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.response.ForgetPasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LupaSandiViewModel : ViewModel() {
    private val _postForgetPass = MutableLiveData<ForgetPasswordResponse>()
    val postForgetPass: LiveData<ForgetPasswordResponse> = _postForgetPass

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun forgetPassword(
        context: Context,
        email: String,
        securityAnswer: String
    ) {
        _isLoading.value = true
        val forgetPass = ForgetPasswordRequest(email, securityAnswer)
        val client = ApiConfig.getApiService().postForgetPassword(forgetPass)
        client.enqueue(object : Callback<ForgetPasswordResponse> {
            override fun onResponse(
                call: Call<ForgetPasswordResponse>,
                response: Response<ForgetPasswordResponse>
            ) {
                if (response.isSuccessful) {
                    _postForgetPass.value = response.body()
                    _isLoading.value= false
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<ForgetPasswordResponse>, t: Throwable) {
//                showErrorDialog("Error: ${t.message}")
                _isLoading.value = false
                Toast.makeText(context, t.message, Toast.LENGTH_LONG)
                    .show()

            }

        })
    }

}