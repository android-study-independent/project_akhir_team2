package com.msib.growsmart.ui.forgetPassword

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
import com.msib.growsmart.databinding.ActivityLupaSandiBinding
import com.msib.growsmart.response.ForgetPasswordResponse
import com.msib.growsmart.ui.resetPassword.NewPasswordActivity

class LupaSandiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLupaSandiBinding
    private val lupaSandiViewModel by viewModels<LupaSandiViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaSandiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initObserver()
    }

    private fun initObserver() {
        lupaSandiViewModel.postForgetPass.observe(this) {
            postForgetPass(it)
        }
        lupaSandiViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }
    @SuppressLint("CheckResult")
    private fun initListener() {
        with(binding) {
            btnNext.setOnClickListener{
                if (binding.etEmail.text.toString().isEmpty() && binding.etAnswerQuestion.text.toString().isEmpty()
                ) {
                    Toast.makeText(
                        this@LupaSandiActivity,
                        "Silakan Masukan Email dan Pertanyaan Keamanan",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    lupaSandiViewModel.forgetPassword(
                        this@LupaSandiActivity,
                        binding.etEmail.text.toString(),
                        binding.etAnswerQuestion.text.toString(),
                    )
                }
            }
        }
    }

    private fun postForgetPass(data: ForgetPasswordResponse) {
        if(data.error == true) {
            if (binding.etEmail.text.toString().isEmpty()) {
                Toast.makeText(
                    this@LupaSandiActivity,
                    "Silakan Masukan Email dengan Benar",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.etAnswerQuestion.text.toString().isEmpty()) {
                Toast.makeText(
                    this@LupaSandiActivity,
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
        val dialogView = inflater.inflate(R.layout.dialog_keamanan_benar, null)

        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.show()

        dialogView.findViewById<Button>(R.id.btnOK).setOnClickListener {
            dialog.dismiss()
            navigateToNewPasswordActivity()
        }
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
