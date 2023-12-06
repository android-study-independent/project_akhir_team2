package com.msib.growsmart.ui.forum.komentar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msib.growsmart.databinding.ActivityKomentarBinding

class KomentarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKomentarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKomentarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}