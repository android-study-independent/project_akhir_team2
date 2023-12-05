package com.msib.growsmart.ui.artikel.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("article/all_article")
    fun getAllArticles(@Header("x-api-key") apiKey: String): Call<ArticlesResponse>
}