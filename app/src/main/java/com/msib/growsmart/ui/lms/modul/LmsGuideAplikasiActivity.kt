package com.msib.growsmart.ui.lms.modul

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
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
        val videoItem = MediaItem.fromUri("https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4")
        val player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            exoPlayer.setMediaItem(videoItem)
            exoPlayer.prepare()
        }
        binding.playerView.player = player
    }


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LmsGuideAplikasiActivity::class.java)
            context.startActivity(starter)
        }
    }

}