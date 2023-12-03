package com.msib.growsmart.ui.beranda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewAllArticleBinding
import com.msib.growsmart.response.ArticlesItem

class BerandaArticleAdapter(private val listArticle: List<ArticlesItem>) : RecyclerView.Adapter<BerandaArticleAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewAllArticleBinding.bind(view)

        fun bindItem(data: ArticlesItem){
            with(binding) {
                if(data.description != null || data.title != null) {
                    tvJudul.text = data.title
                    tvDeskripsi.text = "${data.description}"
                }else {
                    tvJudul.text = "Data masih Kosong"
                    tvDeskripsi.text = "Data masih Kosong"
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