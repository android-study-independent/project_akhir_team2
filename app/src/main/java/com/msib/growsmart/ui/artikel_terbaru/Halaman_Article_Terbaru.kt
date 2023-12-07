package com.msib.growsmart.ui.artikel_terbaru

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.ui.artikel_terbaru.data.Article
import com.msib.growsmart.ui.artikel_terbaru.data.ArticleAdapter
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class Halaman_Article_Terbaru : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_article_terbaru)

        recyclerView = findViewById(R.id.recyclearView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter()
        recyclerView.adapter = adapter

        val id = 1
        val url = "http://195.35.32.179:8002/article/article_id/$id"
        val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyOCwiaWF0IjoxNzAwNzQ2NzIyfQ.ODFt_XcqGHfUGVi5MozPMpoCi67zZGHdUh1zJtCBxbs"

        val request = Request.Builder()
            .url(url)
            .addHeader("x-api-key", apiKey)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle failure
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                runOnUiThread {
                    if (responseData != null) {
                        val jsonObject = JSONObject(responseData)
                        val title = jsonObject.optString("title")
                        val image = jsonObject.optString("image")
                        val description = jsonObject.optString("description")

                        // Add the retrieved data to the adapter
                        adapter.addItem(Article(title, image, description))
                    }
                }
            }
        })
    }
}
