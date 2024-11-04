package com.moondroid.project01_meetingapp.ui.features.sign.social

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class SocialSignData(
    val id: String = "",
    val name: String = "",
    val thumb: String = ""
): Parcelable {
    fun isEmpty() = id.isEmpty() || name.isEmpty() || thumb.isEmpty()
}