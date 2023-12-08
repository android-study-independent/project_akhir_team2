package com.msib.growsmart.ui.forum.komentar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewKomentarItemBinding
import com.msib.growsmart.response.Komentars

class KomentarAdapter(private val listAllKomentar: List<Komentars>): RecyclerView.Adapter<KomentarAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewKomentarItemBinding.bind(view)

        fun bindItem(data: Komentars){
            with(binding) {
                    tvNama.text = data.name
                    tvIsi.text = data.isi
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_komentar_item, parent, false)
        )
    }

    override fun getItemCount(): Int = listAllKomentar.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAllKomentar[position])

    }

}