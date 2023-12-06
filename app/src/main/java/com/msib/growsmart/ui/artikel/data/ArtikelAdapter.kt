package com.msib.growsmart.ui.artikel.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.squareup.picasso.Picasso

class ArtikelAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_article, parent, false)
        return ArtikelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        val currentArticle = articles[position]

        holder.title.text = currentArticle.title
        holder.description.text = currentArticle.description
        Picasso.get().load(currentArticle.image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArtikelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvJudul)
        val description: TextView = itemView.findViewById(R.id.tvDeskripsi)
        val image: ImageView = itemView.findViewById(R.id.ivArticle)
    }
}
