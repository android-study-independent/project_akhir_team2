package com.msib.growsmart.data.request

import com.google.gson.annotations.SerializedName

data class NewPasswordRequest(
    val email: String,
    @SerializedName("newPassword")
    val newPassword: String
)
