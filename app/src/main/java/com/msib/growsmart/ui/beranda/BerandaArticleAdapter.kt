package com.msib.growsmart.ui.beranda

import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewAllArticleBinding
import com.msib.growsmart.response.ArticlesItem
import com.msib.growsmart.ui.artikel.ArtikelActivity
import com.squareup.picasso.Picasso

class BerandaArticleAdapter(private val listArticle: List<ArticlesItem>) : RecyclerView.Adapter<BerandaArticleAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewAllArticleBinding.bind(view)

        fun bindItem(data: ArticlesItem) {
            with(binding) {
                tvJudul.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(data.title ?: "", Html.FROM_HTML_MODE_LEGACY) as Spanned
                } else {
                    @Suppress("DEPRECATION")
                    Html.fromHtml(data.title ?: "") as Spanned
                }

                tvDeskripsi.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(data.description ?: "", Html.FROM_HTML_MODE_LEGACY) as Spanned
                } else {
                    @Suppress("DEPRECATION")
                    Html.fromHtml(data.description ?: "") as Spanned
                }

                // Memuat gambar jika URL gambar tersedia
                data.image?.let { imageUrl ->
                    Picasso.get().load(imageUrl).into(ivArticle)
                }

                // Set onClickListener untuk membuka ArtikelActivity saat item diklik
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ArtikelActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_all_article, parent, false)
        )
    }

    override fun getItemCount(): Int = listArticle.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listArticle[position])
    }
}
