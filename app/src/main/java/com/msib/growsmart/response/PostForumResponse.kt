package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class PostForumResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("Ulasan")
    val ulasan: Review
)

data class Review(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id_user")
    val idUser: Int,

    @field:SerializedName("isi")
    val isi: String
)
