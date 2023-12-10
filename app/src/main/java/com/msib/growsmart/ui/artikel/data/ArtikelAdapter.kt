package com.msib.growsmart.ui.artikel.data


import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.ui.detail_artikel.ArtikelDetailActivity
import com.squareup.picasso.Picasso

class ArtikelAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_article, parent, false)
        return ArtikelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        val currentArticle = articles[position]

        holder.title.text = Html.fromHtml("<h4><small>${currentArticle.title}</small></h4>")
        holder.description.text = Html.fromHtml("<small>${currentArticle.description}</small>" ?: "Tidak ada deskripsi")
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
}
