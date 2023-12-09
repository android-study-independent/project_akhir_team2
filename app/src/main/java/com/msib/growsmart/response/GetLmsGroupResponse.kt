package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class GetLmsGroupResponse(

	@field:SerializedName("Modul")
	val modul: Moduls,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Moduls(

	@field:SerializedName("modul")
	val modul: Moduls,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("benefit")
	val benefit: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
