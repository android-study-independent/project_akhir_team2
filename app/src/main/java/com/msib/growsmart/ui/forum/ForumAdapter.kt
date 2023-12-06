package com.msib.growsmart.ui.forum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewAllForumBinding
import com.msib.growsmart.response.GetAllForumResponseItem
import com.squareup.picasso.Picasso

interface DetailKomentarListener {
    fun onDetailKomentar(forumResponse: GetAllForumResponseItem)
}

class ForumAdapter(private val listAllForum: List<GetAllForumResponseItem>, private val listener: DetailKomentarListener): RecyclerView.Adapter<ForumAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewAllForumBinding.bind(view)

        fun bindItem(data: GetAllForumResponseItem){
            with(binding) {
                Picasso.get().load(data.image).into(ivImage)
                tvPengguna.text = data.nama
                tvDeskripsi.text = data.isi
                btnKomentari.setOnClickListener {
                    listener.onDetailKomentar(data)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_all_forum, parent, false)
        )
    }

    override fun getItemCount(): Int = listAllForum.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAllForum[position])
    }
}