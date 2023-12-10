package com.msib.growsmart.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GetAllForumResponseItem(

	@field:SerializedName("image")
	val image: String?,

	@field:SerializedName("jumlahKomentar")
	val jumlahKomentar: Int,

	@field:SerializedName("nama")
	val nama: String?,

	@field:SerializedName("forumId")
	val forumId: Int,

	@field:SerializedName("isi")
	val isi: String?,

	@field:SerializedName("waktuUpload")
	val waktuUpload: String?,
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readInt(),
		parcel.readString(),
		parcel.readInt(),
		parcel.readString(),
		parcel.readString()
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(image)
		parcel.writeInt(jumlahKomentar)
		parcel.writeString(nama)
		parcel.writeInt(forumId)
		parcel.writeString(isi)
		parcel.writeString(waktuUpload)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<GetAllForumResponseItem> {
		override fun createFromParcel(parcel: Parcel): GetAllForumResponseItem {
			return GetAllForumResponseItem(parcel)
		}

		override fun newArray(size: Int): Array<GetAllForumResponseItem?> {
			return arrayOfNulls(size)
		}
	}
}