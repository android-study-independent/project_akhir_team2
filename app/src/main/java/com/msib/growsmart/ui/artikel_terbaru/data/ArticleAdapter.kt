package com.msib.growsmart.ui.artikel_terbaru.data

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.squareup.picasso.Picasso

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val articles: MutableList<Article> = mutableListOf()

    fun addItem(article: Article) {
        articles.add(article)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.gbr1)
        private val titleTextView: TextView = itemView.findViewById(R.id.title1)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.des1)

        fun bind(article: Article) {
            titleTextView.text = Html.fromHtml(article.title)
            descriptionTextView.text = Html.fromHtml(article.description ?: "Tidak ada deskripsi")
            Picasso.get().load(article.image).into(imageView)
        }
    }
}

data class Article(val title: String, val image: String, val description: String?)
