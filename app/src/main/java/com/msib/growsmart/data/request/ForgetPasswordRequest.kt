package com.msib.growsmart.data.request

import com.google.gson.annotations.SerializedName

data class ForgetPasswordRequest (
    val email: String,
    @SerializedName("securityAnswer")
    val securityAnswer: String,
)
