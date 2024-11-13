package com.moondroid.project01_meetingapp.core.navigation

import android.os.Bundle
import android.util.Base64
import androidx.navigation.NavType
import com.moondroid.damoim.common.parcelable
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 *  data 클래스에 url 형식이 들어가는 경우 기존 링크에 의해 navigation에서 에러가 발생하기 때문에
 *  Base64 인코딩 후에 데이터 전달
 **/
private inline fun <reified T> encrypt(value: T): String {
    val json = Json.encodeToString(value)
    return Base64.encodeToString(json.toByteArray(), Base64.DEFAULT)
}

private inline fun <reified T> decrypt(value: String): T {
    val dec = Base64.decode(value, Base64.DEFAULT)
    return Json.decodeFromString<T>(String(dec))
}

val SocialSignDataType = object : NavType<SocialSignData>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): SocialSignData? {
        return bundle.parcelable<SocialSignData>(key)
    }

    override fun parseValue(value: String): SocialSignData {
        return decrypt(value)
    }

    override fun serializeAsValue(value: SocialSignData): String {
        return encrypt(value)
    }

    override fun put(bundle: Bundle, key: String, value: SocialSignData) {
        bundle.putParcelable(key, value)
    }
}