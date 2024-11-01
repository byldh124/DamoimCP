package com.moondroid.project01_meetingapp.ui.features.sign.social

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class SocialSignData(
    val id: String = "1",
    val name: String = "1",
    val thumb: String = "1"
): Parcelable