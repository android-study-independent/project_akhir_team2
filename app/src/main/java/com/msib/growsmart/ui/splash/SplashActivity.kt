package com.msib.growsmart.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.msib.growsmart.R
import com.msib.growsmart.databinding.ActivitySplashBinding
import com.msib.growsmart.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tambahkan animasi
        val slideLeft = AnimationUtils.loadAnimation(this, R.anim.slide_splash)
        binding.splashLogo.startAnimation(slideLeft)

        // Optional: Tambahkan delay untuk menampilkan splash screen selama beberapa detik
        Handler().postDelayed({
            // Pindah ke activity utama setelah selesai splash screen
            val mainIntent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 1700)
    }
}
