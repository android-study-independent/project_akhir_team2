package com.msib.growsmart.ui.forum.posting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.msib.growsmart.R
import com.msib.growsmart.databinding.ActivityPostingBinding
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.PostForumResponse
import com.msib.growsmart.ui.beranda.BerandaActivity
import com.msib.growsmart.ui.factory.ViewModelFactory
import com.msib.growsmart.ui.forum.utils.reduceFileImage
import com.msib.growsmart.ui.forum.utils.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class PostingActivity : AppCompatActivity() {
    private var currentImageUri: Uri? = null
    private lateinit var binding: ActivityPostingBinding
    private val postingViewModel by viewModels<PostingViewModel>{
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initMedia()
    }


    private fun initObserver() {
        postingViewModel.getUser().observe(this) {
            if(it.isLogin) {
                token = it.token
                binding.tvUser.text = "Hey, ${it.name}"
            }
        }
    }

    private fun initMedia() {
        with(binding) {
            layoutGambar.setOnClickListener {
                startGallery()
            }
            btnPosting.setOnClickListener {
                uploadImage()
            }
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
            binding.btnPosting.isEnabled = true
            binding.previewImageView.visibility = View.VISIBLE
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.etPosting.text.toString()

            showLoading(true)

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val responseSuccess = apiService.postForum(token, multipartBody, requestBody)
                    Log.e("Posting", responseSuccess.message)
                    showLoading(false)
                    navigateToForum()
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, PostForumResponse::class.java)
                    Log.e("Posting", errorResponse.message)
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.peringatan_gambar_kosong))

    }

    private fun navigateToForum() {
        BerandaActivity.start(this, "Forum")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PostingActivity::class.java)
            context.startActivity(starter)
        }
    }
}