package com.msib.growsmart.ui.lms.modul

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msib.growsmart.databinding.ActivityLmsMenanamBinding

class LmsMenanamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLmsMenanamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLmsMenanamBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LmsMenanamActivity::class.java)
            context.startActivity(starter)
        }
    }
}