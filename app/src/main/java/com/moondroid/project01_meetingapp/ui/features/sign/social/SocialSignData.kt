package com.moondroid.project01_meetingapp.ui.features.sign.social

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class SocialSignData(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("thumb")
    val thumb: String = ""
): Parcelable {
    fun isEmpty() = id.isEmpty() || name.isEmpty() || thumb.isEmpty()
}