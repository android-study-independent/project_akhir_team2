package com.msib.growsmart.network

import com.msib.growsmart.data.request.LoginRequest
import com.msib.growsmart.data.request.RegisterRequest
import com.msib.growsmart.response.GetWeatherResponse
import com.msib.growsmart.response.PostLoginResponse
import com.msib.growsmart.response.PostRegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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

    @GET("weather/weather")
    fun getWeather(
        @Header("x-api-key") apiKey: String,
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("unit") unit: String,
    ): Call<GetWeatherResponse>

}