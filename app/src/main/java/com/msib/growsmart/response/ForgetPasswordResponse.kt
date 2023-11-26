package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class ForgetPasswordResponse(

	@field:SerializedName("data")
	val data: ForgetPasswordData,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ForgetPasswordData(
	val any: Any? = null
)

