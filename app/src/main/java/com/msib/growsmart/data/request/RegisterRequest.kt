package com.msib.growsmart.data.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val name: String,
    @SerializedName("answer_question")
    val answerQuestion: String,
    val email: String,
    val password: String
)
