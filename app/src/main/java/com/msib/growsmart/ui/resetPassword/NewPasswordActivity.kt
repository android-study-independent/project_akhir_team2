package com.msib.growsmart.ui.resetPassword

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
import com.msib.growsmart.databinding.ActivityNewPasswordBinding
import com.msib.growsmart.ui.resetPassword.ApiService

class NewPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPasswordBinding

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://195.35.32.179:8002/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etSignInEmail = binding.etSignInEmail
        val etNewPassword = binding.etNewPassword
        val btnSetNewPassword = binding.btnSetNewPassword

        btnSetNewPassword.setOnClickListener {
            val email = etSignInEmail.text.toString()
            val newPassword = etNewPassword.text.toString()
            setNewPassword(email, newPassword)
        }
    }

    private fun setNewPassword(email: String, newPassword: String) {
        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.setNewPassword(email, newPassword)

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
        builder.setMessage("Password successfully set.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
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
            val starter = Intent(context, NewPasswordActivity::class.java)
            context.startActivity(starter)
        }
    }
}
