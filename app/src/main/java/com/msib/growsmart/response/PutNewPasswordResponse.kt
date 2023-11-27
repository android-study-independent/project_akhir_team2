package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class PutNewPasswordResponse(

	@field:SerializedName("data")
	val data: NewPasswordData,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class NewPasswordData(

	@field:SerializedName("answer_question")
	val answerQuestion: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: Any
)
