package com.msib.growsmart.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.data.request.LoginRequest
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.PostLoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val preference: UserPreference) : ViewModel() {

    private val _postLogin = MutableLiveData<PostLoginResponse>()
    val postLogin: LiveData<PostLoginResponse> = _postLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            preference.saveUser(user)
        }
    }

    fun postLogin(context: Context, email: String, password: String) {
        _isLoading.value = true
        val loginRequest = LoginRequest(email, password)
        val client = ApiConfig.getApiService().postLogin(loginRequest)
        client.enqueue(object : Callback<PostLoginResponse> {
            override fun onResponse(
                call: Call<PostLoginResponse>,
                response: Response<PostLoginResponse>
            ) {

                _isLoading.value = false
                if (response.isSuccessful) {
                    _postLogin.value = response.body()
                    val responseBody = response.body()

                    if (responseBody?.error == false) {
                        val token = response.body()?.token as String
                        val idUser = response.body()?.data?.id
                        val name = response.body()?.data?.name
                        val emailUser = response.body()?.data?.email

                        if (idUser != null && name != null && emailUser != null) {
                            saveUser(UserModel(token, idUser, name, emailUser, true))
                            println("DATA USER == $token, $idUser, $name, $emailUser")
                            UserPreference.setToken(token)
                            Toast.makeText(
                                context,
                                "Selamat Datang di GrowSmart",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("login", responseBody.token)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Silakan Cek Email dan Password Anda Kembali",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(context, "Fail: ${response.message()}", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(call: Call<PostLoginResponse>, t: Throwable) {
                _isLoading.value = false
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }


}