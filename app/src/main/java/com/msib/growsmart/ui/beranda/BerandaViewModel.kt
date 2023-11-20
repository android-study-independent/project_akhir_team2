package com.msib.growsmart.ui.beranda

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.preference.UserPreference

class BerandaViewModel (
    private val preference: UserPreference
) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return preference.getUser().asLiveData()
    }
}