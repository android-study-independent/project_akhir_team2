package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class PostLoginResponse(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
) {
    data class Data(

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("id")
        val id: Int,

        @field:SerializedName("email")
        val email: String,

        @field:SerializedName("token")
        val token: String
    )
}