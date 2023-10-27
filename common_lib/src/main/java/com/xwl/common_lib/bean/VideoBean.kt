package com.xwl.common_lib.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author  lxw
 * @date 2023/10/7
 * descripe
 */
data class VideoBean(
    var id: Int = 0,
    var posterUrl: String? = "",
    var videoUrl: String? = "",
    var title: String? = "",
    var type: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(posterUrl)
        parcel.writeString(videoUrl)
        parcel.writeString(title)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoBean> {
        override fun createFromParcel(parcel: Parcel): VideoBean {
            return VideoBean(parcel)
        }

        override fun newArray(size: Int): Array<VideoBean?> {
            return arrayOfNulls(size)
        }
    }
}
