package com.msib.growsmart.ui.forum.posting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.preference.UserPreference

class PostingViewModel(
    private val preference: UserPreference
): ViewModel() {

    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }

}