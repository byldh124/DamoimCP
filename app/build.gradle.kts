import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.google.service)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.android.kotlin.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.kotlin.parcelize)
}

val properties = gradleLocalProperties(rootDir, providers)
val naverClientId: String = properties.getProperty("naver.client.id")
val kakaoClientId: String = properties.getProperty("kakao.client.id")
val googleCredentialServerId: String = properties.getProperty("google.credential.server.id")

android {
    namespace = "com.moondroid.project01_meetingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moondroid.project01_meetingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 33
        versionName = "3.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["naverClientId"] = naverClientId
        manifestPlaceholders["kakaoClientId"] = kakaoClientId
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            resValue("string", "naver_client_id", naverClientId)
            resValue("string", "kakao_client_id", kakaoClientId)
            resValue("string", "google_credential_server_id", googleCredentialServerId)
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "naver_client_id", naverClientId)
            resValue("string", "kakao_client_id", kakaoClientId)
            resValue("string", "google_credential_server_id", googleCredentialServerId)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // navigation - type safe
    implementation(libs.navigation.compose)
    implementation(libs.kotlin.serialization)

    // Hilt dependency injection
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)

    // coil image loader
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // firebase
    implementation(libs.bundles.firebase)

    // Google - Login
    implementation(libs.bundles.google.auth)

    // Kakao
    implementation(libs.kakao.all) // 전체 모듈 설치, 2.11.0 버전부터 지원

    // Naver
    implementation(libs.naver.map)

    implementation(libs.image.cropper)

    //Clean Architecture
    implementation((project(":common")))
    implementation((project(":data")))
    implementation((project(":domain")))
}
