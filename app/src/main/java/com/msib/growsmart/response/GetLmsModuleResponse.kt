package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class GetLmsModuleResponse(

	@field:SerializedName("Modul")
	val modul: Modul,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class IdmodulAccessItem(

	@field:SerializedName("id_modul")
	val idModul: Int,

	@field:SerializedName("status")
	val status: Boolean
)

data class ModulchekUser(

	@field:SerializedName("idmodul_access")
	val idmodulAccess: List<IdmodulAccessItem>
)

data class ModulItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("video")
	val video: String,

	@field:SerializedName("id_group")
	val idGroup: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Modul(

	@field:SerializedName("modulchek_user")
	val modulchekUser: ModulchekUser,

	@field:SerializedName("modul")
	val modul: List<ModulItem>
)
