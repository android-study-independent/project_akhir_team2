package com.msib.growsmart.network

import com.msib.growsmart.data.request.ForgetPasswordRequest
import com.msib.growsmart.data.request.LoginRequest
import com.msib.growsmart.data.request.NewPasswordRequest
import com.msib.growsmart.data.request.RegisterRequest
import com.msib.growsmart.response.CurrentWeather
import com.msib.growsmart.response.ForgetPasswordResponse
import com.msib.growsmart.response.GetWeatherResponse
import com.msib.growsmart.response.PostLoginResponse
import com.msib.growsmart.response.PostRegisterResponse
import com.msib.growsmart.response.PutNewPasswordResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    fun postLogin(
        @Body request: LoginRequest
    ): Call<PostLoginResponse>

    @POST("auth/register")
    fun postRegister(
        @Body request: RegisterRequest
    ): Call<PostRegisterResponse>

    @POST("auth/forget_pass")
    fun postForgetPassword(
        @Body request: ForgetPasswordRequest
    ) : Call<ForgetPasswordResponse>

    @GET("weather/weather")
    fun getWeather(
        @Header("x-api-key") apiKey: String,
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("unit") unit: String,
    ): Call<GetWeatherResponse>

    @GET("weather/weather")
    fun getCurrentWeather(
        @Header("x-api-key") apiKey: String,
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("unit") unit: String,
    ): Call<CurrentWeather>

    @PUT("auth/new_pass")
    fun putNewPassword(
        @Body request: NewPasswordRequest
    ) : Call<PutNewPasswordResponse>

}