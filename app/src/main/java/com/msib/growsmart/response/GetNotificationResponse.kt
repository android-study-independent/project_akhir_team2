package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class GetNotificationResponse(

	@field:SerializedName("formattedForums")
	val formattedForums: List<FormattedForumsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class FormattedForumsItem(

	@field:SerializedName("pesan")
	val pesan: String,

	@field:SerializedName("comments")
	val comments: List<CommentsItem>,

	@field:SerializedName("gambar")
	val gambar: String
)

data class CommentsItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("fill")
	val fill: String
)
