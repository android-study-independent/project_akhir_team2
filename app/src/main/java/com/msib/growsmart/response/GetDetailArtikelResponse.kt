package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class GetDetailArtikelResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("article")
	val article: String,

	@field:SerializedName("timestamp")
	val timestamp: String
)
