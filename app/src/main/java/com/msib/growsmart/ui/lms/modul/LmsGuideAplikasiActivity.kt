package com.msib.growsmart.ui.lms.modul

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.msib.growsmart.databinding.ActivityLmsGuideAplikasiBinding

class LmsGuideAplikasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLmsGuideAplikasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLmsGuideAplikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVideo()
    }

    private fun initVideo() {
        val videoUrl  = "https://youtu.be/Q3tSTO5doJ4?si=d41jTBhJpIHgMHjW"
        val rep = videoUrl.replace("https://youtu.be", "https://www.youtube.com/embed")
        val video = "<iframe width=\"350\" height=\"300\" src=\"$rep\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        with(binding) {
            playerView.loadData(video,"text/html", "utf-8")
            playerView.settings.javaScriptEnabled = true
            playerView.webChromeClient = WebChromeClient()
        }
    }


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LmsGuideAplikasiActivity::class.java)
            context.startActivity(starter)
        }
    }

}