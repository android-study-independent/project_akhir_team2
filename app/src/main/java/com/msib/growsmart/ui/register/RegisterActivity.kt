package com.msib.growsmart.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jakewharton.rxbinding2.widget.RxTextView
import com.msib.growsmart.databinding.ActivityRegisterBinding
import com.msib.growsmart.response.PostRegisterResponse
import com.msib.growsmart.ui.login.LoginActivity
import io.reactivex.Observable

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initObserver()
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        with(binding){
            tvRegister.setOnClickListener {
                LoginActivity.start(this@RegisterActivity)
            }

            btnBuatAkun.setOnClickListener {
                if (binding.etNama.text.toString().isEmpty() && binding.etEmail.text.toString()
                        .isEmpty() && binding.etSandi.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Silakan Masukan Nama, Email, dan Password",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    registerViewModel.postRegister(
                        this@RegisterActivity,
                        binding.etNama.text.toString(),
                        binding.etPengingat.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etSandi.text.toString()
                    )
                }
            }

            val namaStream = RxTextView.textChanges(etNama)
                .skipInitialValue()
                .map { nama ->
                    namaValidate(nama.toString()) && nama.length > 2
                }
            namaStream.subscribe { isNamaValid ->
                if (!isNamaValid) {
                    etNama.error = "Harap masukkan nama Anda dengan benar"
                }
            }

            val emailStream = RxTextView.textChanges(etEmail)
                .skipInitialValue()
                .map { email ->
                    // regex email
                    Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length > 5
                }
            emailStream.subscribe { isEmailValid ->
                if (!isEmailValid) {
                    etEmail.error = "Harap masukkan email Anda dengan benar!"
                }
            }

            val passwordStream = RxTextView.textChanges(etSandi)
                .skipInitialValue()
                .map { password ->
                    passwordValidate(password.toString())
                }
            passwordStream.subscribe { isPasswordValid ->
                if (!isPasswordValid) {
                    etSandi.setError(
                        "Password harus mengandung minimal 6 karakter yang terdiri dari 1 huruf besar, 1 huruf kecil, dan 1 angka",
                        null
                    )
                }
            }

            Observable.combineLatest(
                namaStream,
                emailStream,
                passwordStream
            ) { namaValid: Boolean, emailValid: Boolean, passwordValid: Boolean ->
                namaValid && emailValid && passwordValid
            }.subscribe { isButtonValid ->
                binding.btnBuatAkun.isEnabled = isButtonValid
            }

        }
    }

    private fun namaValidate(nama: String): Boolean {
        val namaPattern = "^[a-zA-Z\\s]+$"
        return nama.matches(namaPattern.toRegex())
    }

    private fun passwordValidate(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun initObserver() {
        registerViewModel.postRegister.observe(this) {
            postRegister(it)
        }
        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }
    private fun postRegister(data: PostRegisterResponse) {
        if (data.error == true) {
            if (binding.etNama.text.toString().isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Silakan Masukan Nama dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.etEmail.text.toString().isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Silakan Masukan Email dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.etPengingat.text.toString().isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Silakan Masukan pertanyaan dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.etSandi.text.toString().isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Silakan Masukan Password dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(this@RegisterActivity, data.message, Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            LoginActivity.start(this)
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RegisterActivity::class.java)
            context.startActivity(starter)
        }
    }
}