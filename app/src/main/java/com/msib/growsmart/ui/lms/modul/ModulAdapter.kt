package com.msib.growsmart.ui.lms.modul

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewModulItemBinding
import com.msib.growsmart.response.ModulItem

interface PlayModulVideo {
    fun onPlayModulVideo(modulResponse: ModulItem)
}

class ModulAdapter(private val listModul: List<ModulItem>, private val listener: PlayModulVideo): RecyclerView.Adapter<ModulAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewModulItemBinding.bind(view)

        fun bindItem(data: ModulItem){
            with(binding) {
                tvModul.text = data.title
                cvModul.setOnClickListener {
                    listener.onPlayModulVideo(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_modul_item, parent, false)
        )
    }

    override fun getItemCount(): Int = listModul.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listModul[position])
    }

}