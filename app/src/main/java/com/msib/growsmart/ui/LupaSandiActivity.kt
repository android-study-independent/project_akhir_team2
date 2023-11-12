package com.msib.growsmart.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.msib.growsmart.R
import com.msib.growsmart.databinding.ActivityLupaSandiBinding

class LupaSandiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLupaSandiBinding // Declare the binding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaSandiBinding.inflate(layoutInflater) // Initialize the binding
        setContentView(binding.root)

        val etSignInEmail = binding.etSignInEmail
        val etPengingat = binding.etPengingat
        val btnNext = binding.btnNext

        binding.btnNext.setOnClickListener {
            if (isValidInput()) {
                showSuccessDialog()
            } else {
                showErrorDialog("Invalid input. Please check your email and reminder.")
            }
        }
    }

    private fun isValidInput(): Boolean {
        val email = binding.etSignInEmail.text.toString()
        val reminder = binding.etPengingat.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        return true
    }

    private fun showSuccessDialog() {
        val view = View.inflate(this@LupaSandiActivity, R.layout.dialog_sukses, null)

        val builder = AlertDialog.Builder(this@LupaSandiActivity)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun showErrorDialog(errorMessage: String) {
        val builder = AlertDialog.Builder(this@LupaSandiActivity)
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
