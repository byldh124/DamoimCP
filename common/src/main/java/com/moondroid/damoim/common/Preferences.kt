package com.moondroid.damoim.common

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object Preferences {

    object PrefsKey {
        const val AUTO_SIGN = "AUTO_SIGN"
    }

    private lateinit var preferences: SharedPreferences
    private const val PREF_NAME = "sehan_pref"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    private fun getString(key: String, defVal: String = ""): String {
        return preferences.getString(key, defVal) ?: defVal
    }

    private fun putString(key: String, value: String) {
        preferences.edit {
            putString(key, value)
        }
    }

    private fun getBoolean(key: String, defVal: Boolean = false): Boolean {
        return preferences.getBoolean(key, defVal)
    }

    private fun putBoolean(key: String, value: Boolean) {
        preferences.edit {
            putBoolean(key, value)
        }
    }

    fun setAutoSign(auto: Boolean) = putBoolean(PrefsKey.AUTO_SIGN, auto)
    fun isAutoSign() = getBoolean(PrefsKey.AUTO_SIGN)

    fun clear() {
        preferences.edit().clear().apply()
    }
}