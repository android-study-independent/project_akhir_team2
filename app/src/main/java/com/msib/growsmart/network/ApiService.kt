package com.msib.growsmart.network

import com.msib.growsmart.response.PostLoginResponse
import com.msib.growsmart.response.PostRegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun loginWithToken(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<PostLoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun registerAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Call<PostRegisterResponse>
}