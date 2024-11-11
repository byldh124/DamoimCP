package com.moondroid.project01_meetingapp

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 카카오 sdk 초기화
        KakaoSdk.init(this, getString(R.string.kakao_client_id))
        // 네이버 맵 클라이언트 초기화
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(getString(R.string.naver_client_id))
        // Firebase 초기화
        FirebaseApp.initializeApp(applicationContext)
    }
}