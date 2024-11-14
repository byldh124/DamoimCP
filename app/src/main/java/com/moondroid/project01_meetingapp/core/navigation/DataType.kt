package com.moondroid.project01_meetingapp.core.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.moondroid.damoim.common.parcelable
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val SocialSignDataType = object : NavType<SocialSignData>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): SocialSignData? {
        return bundle.parcelable<SocialSignData>(key)
    }

    override fun parseValue(value: String): SocialSignData {
        val temp = Json.decodeFromString<SocialSignData>(value)
        return temp.copy(thumb = Uri.decode(temp.thumb))
    }

    override fun serializeAsValue(value: SocialSignData): String {
        val temp = value.copy(thumb = Uri.encode(value.thumb))
        return Json.encodeToString(temp)
    }

    override fun put(bundle: Bundle, key: String, value: SocialSignData) {
        bundle.putParcelable(key, value)
    }
}