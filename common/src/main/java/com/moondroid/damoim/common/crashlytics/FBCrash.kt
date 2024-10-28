package com.moondroid.damoim.common.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics

object FBCrash {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    fun setProperty(userId: String) {
        crashlytics.setUserId(userId)
    }

    fun report(t: Throwable) {
        val message = t.stackTraceToString()
        crashlytics.log("[logException]::$message")
    }
}