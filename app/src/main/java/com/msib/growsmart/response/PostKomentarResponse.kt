package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class PostKomentarResponse (

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("Balasan")
    val balasan: AktivitasKomentar,

)

data class AktivitasKomentar(

    @field:SerializedName("id_user")
    val idUser: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("isi")
    val isi: Boolean,

)