package com.msib.growsmart.ui.forum.komentar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msib.growsmart.databinding.ActivityKomentarBinding
import com.msib.growsmart.response.GetAllForumResponseItem

class KomentarActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityKomentarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKomentarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val komentar = intent.getParcelableExtra<GetAllForumResponseItem>("komentar")

        if(komentar != null) {
            with(binding) {
                tvUser.text = komentar.nama
                tvIsiKomentar.text = komentar.isi
                tvJumlahKomentar.text = "${komentar.jumlahKomentar} Komentar"
            }
        }

    }
}