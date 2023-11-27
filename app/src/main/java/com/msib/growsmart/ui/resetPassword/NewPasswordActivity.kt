package com.msib.growsmart.ui.resetPassword

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.msib.growsmart.R
import com.msib.growsmart.databinding.ActivityNewPasswordBinding
import com.msib.growsmart.response.PutNewPasswordResponse
import com.msib.growsmart.ui.login.LoginActivity

class NewPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPasswordBinding
    private val newPasswordViewModel by viewModels<NewPasswordViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initObserver()
    }

    private fun initObserver() {
        newPasswordViewModel.putNewPass.observe(this) {
            putNewPass(it)
        }
        newPasswordViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        with(binding) {
            btnSetNewPassword.setOnClickListener{
                if (binding.etSignInEmail.text.toString().isEmpty() && binding.etNewPassword.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        this@NewPasswordActivity,
                        "Silakan Masukan Email dan Pertanyaan Keamanan",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    newPasswordViewModel.putNewPass(
                        this@NewPasswordActivity,
                        binding.etSignInEmail.text.toString(),
                        binding.etNewPassword.text.toString(),
                    )
                }
            }
        }
    }

    private fun putNewPass(data: PutNewPasswordResponse) {
        if(data.error == true) {
            if (binding.etSignInEmail.text.toString().isEmpty()) {
                Toast.makeText(
                    this@NewPasswordActivity,
                    "Silakan Masukan Email dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.etNewPassword.text.toString().isEmpty()) {
                Toast.makeText(
                    this@NewPasswordActivity,
                    "Silakan Masukan Pertanyaan Keamanan dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            showSuccessDialog()
        }
    }

    private fun showLoading(value: Boolean) {
        binding.progressBar.isVisible = value
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_sukses, null)

        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.show()

        dialogView.findViewById<Button>(R.id.btnOK).setOnClickListener {
            dialog.dismiss()
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity() {
        LoginActivity.start(this)
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
            val starter = Intent(context, NewPasswordActivity::class.java)
            context.startActivity(starter)
        }
    }
}
