package com.msib.growsmart.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.msib.growsmart.R
import com.msib.growsmart.databinding.ActivitySplashBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.ui.beranda.BerandaActivity
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.login.LoginActivity
import com.msib.growsmart.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModels<SplashViewModel> {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private lateinit var preference: UserPreference
    private val activityScope = CoroutineScope(Dispatchers.Main)
    private var isLogin: Boolean = false
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference.getInstance(dataStore)
        initToken()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        activityScope.launch {
            delay(Constant.DELAY_SPLASH_SCREEN)
            runOnUiThread {
                if (isLogin) {
                    BerandaActivity.start(this@SplashActivity, "Beranda")
                } else {
                    LoginActivity.start(this@SplashActivity)
                }
                finish()
            }
        }


//        // Tambahkan animasi
//        val slideLeft = AnimationUtils.loadAnimation(this, R.anim.slide_splash)
//        binding.splashLogo.startAnimation(slideLeft)
//
//        // Optional: Tambahkan delay untuk menampilkan splash screen selama beberapa detik
//        Handler().postDelayed({
//            // Pindah ke activity utama setelah selesai splash screen
//            val mainIntent = Intent(this@SplashActivity, LoginActivity::class.java)
//            startActivity(mainIntent)
//            finish()
//        }, 1700)
    }

    private fun initToken() {
        viewModel.getUser().observe(this) {
            isLogin = if (it.isLogin) {
                UserPreference.setToken(it.token)
                true
            } else {
                false
            }
        }
    }

}
