package com.msib.growsmart.ui.forum.notifikasi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.RecyclerviewNotificationItemBinding
import com.msib.growsmart.response.FormattedForumsItem
import com.squareup.picasso.Picasso

class NotifikasiAdapter(private val listNotifikasi: List<FormattedForumsItem>): RecyclerView.Adapter<NotifikasiAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecyclerviewNotificationItemBinding.bind(view)

        fun bindItem(data: FormattedForumsItem){
            with(binding) {
                tvNotif.text = data.pesan
                Picasso.get().load(data.gambar).into(ivImage)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_notification_item, parent, false)
        )
    }

    override fun getItemCount(): Int = listNotifikasi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listNotifikasi[position])

    }

}
