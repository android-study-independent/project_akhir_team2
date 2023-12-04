package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class GetAllForumResponse(

	@field:SerializedName("GetAllForumResponse")
	val getAllForumResponse: List<GetAllForumResponseItem>
)

data class GetAllForumResponseItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("jumlahKomentar")
	val jumlahKomentar: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("forumId")
	val forumId: Int,

	@field:SerializedName("isi")
	val isi: String
)
