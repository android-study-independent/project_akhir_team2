package com.msib.growsmart.ui.lms.modul

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msib.growsmart.databinding.ActivityLmsMetanBinding

class LmsMetanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLmsMetanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLmsMetanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LmsMetanActivity::class.java)
            context.startActivity(starter)
        }
    }
}