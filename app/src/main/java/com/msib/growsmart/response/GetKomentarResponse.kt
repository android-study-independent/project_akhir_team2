package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class GetKomentarResponse(

    @field:SerializedName("forum")
    val forum: Forum,

    @field:SerializedName("komentars")
    val komentars: List<Komentars>

)

data class Forum(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id_user")
    val idUser: Int,

    @field:SerializedName("isi")
    val isi: String
)

data class Komentars(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id_user")
    val idUser: Int,

    @field:SerializedName("isi")
    val isi: String
)