package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class GetAllArticleResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem>
)

data class ArticlesItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

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
	val timestamp: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
