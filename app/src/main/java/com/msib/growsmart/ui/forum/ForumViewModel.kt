package com.msib.growsmart.ui.forum

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.preference.UserPreference
import com.msib.growsmart.response.GetAllForumResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumViewModel(
    private val preference: UserPreference
) : ViewModel() {
    private var _getAllForum = MutableLiveData<List<GetAllForumResponseItem>>()
    val getAllForum : LiveData<List<GetAllForumResponseItem>> = _getAllForum

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

    fun getAllForum(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllForum(token)
        client.enqueue(object: Callback<List<GetAllForumResponseItem>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<List<GetAllForumResponseItem>>,
                response: Response<List<GetAllForumResponseItem>>
            ) {
                _isLoading.value = false
                    if(response.isSuccessful) {
                        _getAllForum.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure : ${response.message()}")
                    }
            }

            override fun onFailure(call: Call<List<GetAllForumResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }


    companion object {
        const val TAG = "Forum"
    }

}