package com.msib.growsmart.ui.artikel_terbaru

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.ui.artikel_terbaru.data.Article
import com.msib.growsmart.ui.artikel_terbaru.data.ArticleAdapter
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class HalamanArticleTerbaruActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_article_terbaru)


        recyclerView = findViewById(R.id.recyclearView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter()
        recyclerView.adapter = adapter

        val ids = listOf(1) // List of IDs you want to retrieve
        val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyOCwiaWF0IjoxNzAwNzQ2NzIyfQ.ODFt_XcqGHfUGVi5MozPMpoCi67zZGHdUh1zJtCBxbs"

        val client = OkHttpClient()

        ids.forEach { id ->
            val url = "http://195.35.32.179:8002/article/article_id/$id"

            val request = Request.Builder()
                .url(url)
                .addHeader("x-api-key", apiKey)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle failure for each ID
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) {
                            // Handle unsuccessful response for each ID
                            throw IOException("Unexpected code $response")
                        }

                        val responseData = response.body?.string()

                        runOnUiThread {
                            responseData?.let {
                                val jsonObject = JSONObject(it)
                                val title = jsonObject.optString("title")
                                val image = jsonObject.optString("image")
                                val description = jsonObject.optString("description")

                                // Add the retrieved data to the adapter
                                adapter.addItem(Article(title, image, description))
                            }
                        }
                    }
                }
            })
        }
    }
}
