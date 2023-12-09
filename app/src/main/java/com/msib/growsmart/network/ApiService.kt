package com.msib.growsmart.network

import com.msib.growsmart.data.request.ForgetPasswordRequest
import com.msib.growsmart.data.request.KirimKomentarRequest
import com.msib.growsmart.data.request.LoginRequest
import com.msib.growsmart.data.request.NewPasswordRequest
import com.msib.growsmart.data.request.RegisterRequest
import com.msib.growsmart.response.ForgetPasswordResponse
import com.msib.growsmart.response.GetAllArticleResponse
import com.msib.growsmart.response.GetAllForumResponseItem
import com.msib.growsmart.response.GetKomentarResponse
import com.msib.growsmart.response.GetLmsGroupResponse
import com.msib.growsmart.response.GetWeatherResponse
import com.msib.growsmart.response.PostForumResponse
import com.msib.growsmart.response.PostKomentarResponse
import com.msib.growsmart.response.PostLoginResponse
import com.msib.growsmart.response.PostRegisterResponse
import com.msib.growsmart.response.PutNewPasswordResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
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

    @PUT("auth/new_pass")
    fun putNewPassword(
        @Body request: NewPasswordRequest
    ) : Call<PutNewPasswordResponse>

    @GET("article/all_article")
    fun getAllArticle(
        @Header("x-api-key") apiKey: String,
    ): Call<GetAllArticleResponse>

    @GET("forum/allforum")
    fun getAllForum(
        @Header("x-api-key") apiKey: String,
    ): Call<List<GetAllForumResponseItem>>

    @Multipart
    @POST("forum/upload_and_add_forum")
    suspend fun postForum(
        @Header("x-api-key") apiKey: String,
        @Part file: MultipartBody.Part,
        @Part("isi") isi: RequestBody
    ): PostForumResponse

    @POST("forum/balasan/{id_forum}")
    fun postKomentar(
        @Header("x-api-key") apiKey: String,
        @Path("id_forum") idForum: String,
        @Body request: KirimKomentarRequest
    ): Call<PostKomentarResponse>

    @GET("forum/forum/{id}")
    fun getKomentar(
        @Header("x-api-key") apiKey: String,
        @Path("id") id: String,
    ): Call<GetKomentarResponse>

    @GET("lms/getgroup/{id_group}")
    fun getLmsGroup(
        @Header("x-api-key") apiKey: String,
        @Path("id_group") idGroup: String,
    ): Call<GetLmsGroupResponse>

}