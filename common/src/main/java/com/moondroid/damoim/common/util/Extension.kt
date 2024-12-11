package com.moondroid.damoim.common.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.annotation.Keep
import com.moondroid.damoim.common.BuildConfig
import java.security.MessageDigest

fun Any.debug(msg: String) {
    if (BuildConfig.DEBUG) {
        Log.e("Damoim", "[${simpleName()}] | $msg")
    }
}

fun debug(msg: String) {
    if (BuildConfig.DEBUG) {
        Log.e("Damoim", msg)
    }
}

fun Any.logException(e: Throwable) {
    if (BuildConfig.DEBUG) {
        Log.e("Damoim", "[ ${simpleName()} || logException ] -> ${e.printStackTrace()}")
    }
}

fun Any.simpleName() = javaClass.simpleName.trim()

@Keep
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> this.getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") this.getParcelable(key) as? T
}


fun hashingPw(password: String, salt: String): String {
    val md: MessageDigest = MessageDigest.getInstance("SHA-256")  // SHA-256 해시함수를 사용
    var output: ByteArray = password.toByteArray(Charsets.UTF_8)

    // key-stretching
    for (i in 0..999) {
        val temp: String = byteToString(output) + salt      // 패스워드와 Salt 를 합쳐 새로운 문자열 생성
        md.update(temp.toByteArray(Charsets.UTF_8))        // temp 의 문자열을 해싱하여 md 에 저장해둔다
        output = md.digest()                                // md 객체의 다이제스트를 얻어 password 를 갱신한다
    }

    return byteToString(output)
}

fun byteToString(byteArray: ByteArray): String {
    val sb = StringBuilder()
    for (bt in byteArray) {
        sb.append(String.format("%02x", bt))
    }
    return sb.toString()
}


