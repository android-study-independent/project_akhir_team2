package com.msib.growsmart.data

data class UserModel (
    val token: String,
    val idUser: Int,
    val name: String,
    val email: String,
    val isLogin: Boolean
)