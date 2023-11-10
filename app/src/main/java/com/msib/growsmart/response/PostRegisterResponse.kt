package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class PostRegisterResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("data")
    val data: RegisterData,

    @field:SerializedName("message")
    val message: String
)

data class RegisterData(

    @field:SerializedName("answer_question")
    val answerQuestion: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String
)