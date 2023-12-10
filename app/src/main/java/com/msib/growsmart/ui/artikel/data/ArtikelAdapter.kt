package com.msib.growsmart.ui.artikel.data


import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.ui.detail_artikel.ArtikelDetailActivity
import com.squareup.picasso.Picasso

class ArtikelAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_article, parent, false)
        return ArtikelViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        val currentArticle = articles[position]

        val titleText = "<h4>${currentArticle.title}</h4>"
        val descriptionText = currentArticle.description ?: "Tidak ada deskripsi"

        holder.title.text = fromHtmlWithFontSize(titleText, 14)
        holder.description.text = fromHtmlWithFontSize(descriptionText, 12)

        Picasso.get().load(currentArticle.image).into(holder.image)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ArtikelDetailActivity::class.java)
            intent.putExtra(ArtikelDetailActivity.EXTRA_ARTICLE, currentArticle)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArtikelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvJudul)
        val description: TextView = itemView.findViewById(R.id.tvDeskripsi)
        val image: ImageView = itemView.findViewById(R.id.ivArticle)
    }

    @RequiresApi(Build.VERSION_CODES.N)
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
