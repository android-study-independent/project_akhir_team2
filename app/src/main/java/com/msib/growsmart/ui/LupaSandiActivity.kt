package com.msib.growsmart.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.msib.growsmart.databinding.ActivityLupaSandiBinding
import com.msib.growsmart.ui.resetPassword.ApiService
import com.msib.growsmart.ui.resetPassword.NewPasswordActivity

class LupaSandiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLupaSandiBinding

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://195.35.32.179:8002/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaSandiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etSignInEmail = binding.etSignInEmail
        val etSecurityAnswer = binding.etPengingat
        val btnNext = binding.btnNext

        btnNext.setOnClickListener {
            val email = etSignInEmail.text.toString()
            val securityAnswer = etSecurityAnswer.text.toString()
            resetPassword(email, securityAnswer)
        }
    }

    private fun resetPassword(email: String, securityAnswer: String) {
        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.setNewPassword(email, securityAnswer)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    showSuccessDialog()
                } else {
                    showErrorDialog("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showErrorDialog("Error: ${t.message}")
            }
        })
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Success")
        builder.setMessage("Password reset instructions sent to your email.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            navigateToNewPasswordActivity()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun navigateToNewPasswordActivity() {
        NewPasswordActivity.start(this)
        finish()
    }

    private fun showErrorDialog(errorMessage: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(errorMessage)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LupaSandiActivity::class.java)
            context.startActivity(starter)
        }
    }
}
