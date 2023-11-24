package com.msib.growsmart.ui.resetPassword

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("auth/forget_pass")
    fun forgetPassword(
        @Field("email") email: String,
        @Field("securityAnswer") securityAnswer: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("auth/new_pass")
    fun setNewPassword(
        @Field("email") email: String,
        @Field("newPassword") newPassword: String
    ): Call<ResponseBody>
}
