package com.msib.growsmart.ui.forum

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.response.GetAllForumResponse
import com.msib.growsmart.response.GetAllForumResponseItem
import com.msib.growsmart.utils.Constant.X_API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumViewModel : ViewModel() {
    private var _getAllForum = MutableLiveData<List<GetAllForumResponseItem>>()
    val getAllForum : LiveData<List<GetAllForumResponseItem>> = _getAllForum

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getAllForum() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllForum(X_API_KEY)
        client.enqueue(object: Callback<GetAllForumResponse> {
            override fun onResponse(
                call: Call<GetAllForumResponse>,
                response: Response<GetAllForumResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _getAllForum.value = response.body()?.getAllForumResponse
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GetAllForumResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val TAG = "Cuaca"
    }

}