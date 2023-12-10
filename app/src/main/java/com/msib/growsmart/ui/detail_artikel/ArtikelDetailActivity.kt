package com.msib.growsmart.ui.detail_artikel

import android.content.Context
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.msib.growsmart.databinding.ItemDetailArticleBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.ui.artikel.data.Article
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ArtikelDetailActivity : AppCompatActivity() {

    private lateinit var article: Article
    private lateinit var binding: ItemDetailArticleBinding
    private val artikelViewModel by viewModels<ArtikelDetailViewModel>{
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var token: String
    private lateinit var idArtikel: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)!!

        with(binding){
            tvAuthor.text = article.name ?: "Nama Penulis Tidak Tersedia"
            tvTanggal.text = article.timestamp
            Picasso.get().load(article.image).into(imageView)
        }
        initObserver()
    }

    private fun initObserver(){
        artikelViewModel.getUser().observe(this) {
            article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)!!
            if(it.isLogin) {
                    token = it.token
                    idArtikel = article.id.toString()
                    article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)!!
                artikelViewModel.getDetailArtikel(token, idArtikel)
                artikelViewModel.getDetailArtikel.observe(this) { artikel ->
                    with(binding) {
                        val inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
                        val outputPattern = "dd-MM-yyyy"
                        val inputFormatter = DateTimeFormatter.ofPattern(inputPattern)
                        val outputFormatter = DateTimeFormatter.ofPattern(outputPattern)

                        val inputDate = artikel.timestamp
                        val localDate = LocalDate.parse(inputDate, inputFormatter)

                        val outputDate = outputFormatter.format(localDate)

                        tvDeskripsi.text = Html.fromHtml(artikel.article ?: "", Html.FROM_HTML_MODE_LEGACY)
                        tvJudul.text = Html.fromHtml(artikel.title ?: "", Html.FROM_HTML_MODE_LEGACY)
                        tvTanggal.text = outputDate


                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}
