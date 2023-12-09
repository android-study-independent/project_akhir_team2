package com.msib.growsmart.ui.detail_artikel

import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.msib.growsmart.R
import com.msib.growsmart.ui.artikel.data.Article
import com.squareup.picasso.Picasso

class ArtikelDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }

    private lateinit var article: Article
    private lateinit var titleTextView: TextView
    private lateinit var authorTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_detail_article)

        titleTextView = findViewById(R.id.tvJudul)
        authorTextView = findViewById(R.id.tvAuthor)
        dateTextView = findViewById(R.id.tvTanggal)
        descriptionTextView = findViewById(R.id.tvDeskripsi)
        imageView = findViewById(R.id.imageView)

        // Ambil data artikel yang dikirim dari activity sebelumnya
        article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)!!

        // Tampilkan detail artikel
        titleTextView.text = Html.fromHtml(article.title ?: "", Html.FROM_HTML_MODE_LEGACY)
        authorTextView.text = article.name ?: "Nama Penulis Tidak Tersedia"
        dateTextView.text = article.timestamp
        descriptionTextView.text = Html.fromHtml(article.description ?: "", Html.FROM_HTML_MODE_LEGACY)
        Picasso.get().load(article.image).into(imageView)
    }
}
