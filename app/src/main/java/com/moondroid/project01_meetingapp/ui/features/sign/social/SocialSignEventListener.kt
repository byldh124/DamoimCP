package com.moondroid.project01_meetingapp.ui.features.sign.social

interface SocialSignEventListener {
    fun onSuccess(socialSignData: SocialSignData)
    fun onError(throwable: Throwable)
}