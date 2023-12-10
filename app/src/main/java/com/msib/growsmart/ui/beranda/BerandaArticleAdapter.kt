package com.msib.growsmart.ui.beranda

import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewAllArticleBinding
import com.msib.growsmart.response.ArticlesItem
import com.squareup.picasso.Picasso

class BerandaArticleAdapter(private val listArticle: List<ArticlesItem>) : RecyclerView.Adapter<BerandaArticleAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewAllArticleBinding.bind(view)

        fun bindItem(data: ArticlesItem){
            with(binding) {
                tvJudul.text = fromHtmlWithFontSize(data.title ?: "", 14)
                tvDeskripsi.text = fromHtmlWithFontSize(data.description ?: "Tidak ada deskripsi", 12)

                // Memuat gambar jika URL gambar tersedia
                data.image?.let { imageUrl ->
                    Picasso.get().load(imageUrl).into(ivArticle)
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

    private fun fromHtmlWithFontSize(html: String, fontSize: Int): SpannableStringBuilder {
        val spannable: Spannable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, null, null) as Spannable
        } else {
            Html.fromHtml(html) as Spannable
        }

        val start = 0
        val end = spannable.length
        spannable.setSpan(AbsoluteSizeSpan(fontSize, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return SpannableStringBuilder(spannable)
    }
}
