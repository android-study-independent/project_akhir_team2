package com.msib.growsmart.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jakewharton.rxbinding2.widget.RxTextView
import com.msib.growsmart.MainActivity
import com.msib.growsmart.databinding.ActivityLoginBinding
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.PostLoginResponse
import com.msib.growsmart.ui.LupaSandiActivity
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.register.RegisterActivity
import io.reactivex.Observable
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private lateinit var preference: UserPreference
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference.getInstance(dataStore)

        initListener()
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        with(binding) {
            btnLogin.setOnClickListener {
                loginViewModel.postLogin(
                    this@LoginActivity,
                    etSignInEmail.text.toString(),
                    etKataSandi.text.toString()
                )
                initObserver()
            }
            onBackPressedDispatcher.addCallback(this@LoginActivity) {
                exitProcess(0)
            }

            tvRegister.setOnClickListener {
                RegisterActivity.start(this@LoginActivity)
            }

            tvLupaSandi.setOnClickListener {
                LupaSandiActivity.start(this@LoginActivity)
            }

            val emailStream = RxTextView.textChanges(etSignInEmail)
                .skipInitialValue()
                .map { email ->
                    // regex email
                    Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length > 5
                }
            emailStream.subscribe { isEmailValid ->
                if (!isEmailValid) {
                    etSignInEmail.error = "Harap masukkan email Anda dengan benar!"
                }
            }

            val passwordStream = RxTextView.textChanges(etKataSandi)
                .skipInitialValue()
                .map { password ->
                    passwordValidate(password.toString())
                }
            passwordStream.subscribe { isPasswordValid ->
                if (!isPasswordValid) {
                    etKataSandi.setError(
                        "Password harus mengandung minimal 6 karakter yang terdiri dari 1 huruf besar, 1 huruf kecil, dan 1 angka",
                        null
                    )
                }
            }

            Observable.combineLatest(
                emailStream,
                passwordStream
            ) { emailValid: Boolean, passwordValid: Boolean ->
                emailValid && passwordValid
            }.subscribe { isButtonValid ->
                binding.btnLogin.isEnabled = isButtonValid
            }
        }
    }


    private fun passwordValidate(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun initObserver() {
        loginViewModel.postLogin.observe(this) {
            postLogin(it)
        }
        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun postLogin(login: PostLoginResponse) {
        if (!login.error) {
            MainActivity.start(this, "main")
        }
    }


    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }

}