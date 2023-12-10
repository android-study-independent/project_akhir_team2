package com.msib.growsmart.ui.beranda


import android.os.Parcel
import android.os.Parcelable

data class ArticlesItem(
    val title: String?,
    val description: String?,
    val image: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticlesItem> {
        override fun createFromParcel(parcel: Parcel): ArticlesItem {
            return ArticlesItem(parcel)
        }

        override fun newArray(size: Int): Array<ArticlesItem?> {
            return arrayOfNulls(size)
        }
    }
}
