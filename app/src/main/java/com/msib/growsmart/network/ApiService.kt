package com.msib.growsmart.network

import com.msib.growsmart.response.PostLoginResponse
import com.msib.growsmart.response.PostRegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Call<PostLoginResponse>

    @FormUrlEncoded
    @POST("auth/register")
    fun postRegister(
        @Field("name") name: String,
        @Field("answer_question") answerQuestion: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Call<PostRegisterResponse>
}