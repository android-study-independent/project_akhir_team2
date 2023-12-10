package com.msib.growsmart.ui.artikel

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.ui.artikel.data.ApiService
import com.msib.growsmart.ui.artikel.data.Article
import com.msib.growsmart.ui.artikel.data.ArticlesResponse
import com.msib.growsmart.ui.artikel.data.ArtikelAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtikelActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArtikelAdapter
    private lateinit var ivImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_artikel)

        recyclerView = findViewById(R.id.recyclearView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        ivImage = findViewById(R.id.ivImage)


        getAllArticles()

    }

    private fun getAllArticles() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://195.35.32.179:8002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyOCwiaWF0IjoxNzAwNzQ2NzIyfQ.ODFt_XcqGHfUGVi5MozPMpoCi67zZGHdUh1zJtCBxbs"

        val call: Call<ArticlesResponse> = apiService.getAllArticles(apiKey)
        call.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    setupRecyclerView(articles)
                } else {
                    println("Respons tidak berhasil: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                println("Kesalahan jaringan atau permintaan gagal: ${t.message}")
            }
        })
    }




    private fun setupRecyclerView(articles: List<Article>) {
        adapter = ArtikelAdapter(articles)
        recyclerView.adapter = adapter
    }

}