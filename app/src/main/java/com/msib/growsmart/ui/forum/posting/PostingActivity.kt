package com.msib.growsmart.ui.forum.posting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msib.growsmart.R

class PostingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posting)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PostingActivity::class.java)
            context.startActivity(starter)
        }
    }
}