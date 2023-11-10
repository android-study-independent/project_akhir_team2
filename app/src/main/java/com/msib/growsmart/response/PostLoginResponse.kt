package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class PostLoginResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("data")
    val data: LoginData,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("token")
    val token: String
)

data class LoginData(

    @field:SerializedName("answer_question")
    val answerQuestion: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("updatedAt")
    val updatedAt: Any
)