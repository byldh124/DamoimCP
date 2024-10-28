package com.moondroid.damoim.common

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.util.Log
import androidx.annotation.Keep
import com.moondroid.damoim.common.crashlytics.FBCrash
import java.io.Serializable
import java.security.MessageDigest

object Extension {
    fun Any.debug(msg: String) {
        if (BuildConfig.DEBUG) Log.e("Damoim", "[${this.javaClass.simpleName.trim()}] | $msg")
    }

    fun Any.logException(e: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(
                "Damoim",
                "[ ${this.javaClass.simpleName.trim()} || logException ] -> ${e.printStackTrace()}"
            )
        }
        FBCrash.report(e)
    }

    @Keep
    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    @Keep
    inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

    fun hashingPw(password: String, salt: String): String {
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")  // SHA-256 해시함수를 사용
        var output: ByteArray = password.toByteArray(Charsets.UTF_8)

        // key-stretching
        for (i in 0..999) {
            val temp: String = byteToString(output) + salt      // 패스워드와 Salt 를 합쳐 새로운 문자열 생성
            md.update(temp.toByteArray(Charsets.UTF_8))        // temp 의 문자열을 해싱하여 md 에 저장해둔다
            output = md.digest()                                // md 객체의 다이제스트를 얻어 password 를 갱신한다
            debug("output[$i] : ${byteToString(output)}")
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
}


