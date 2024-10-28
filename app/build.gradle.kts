import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.android.kotlin.ksp)
}

val properties = gradleLocalProperties(rootDir, providers)
val naverClientId: String = properties.getProperty("naver.client.id")
val kakaoClientId: String = properties.getProperty("kakao.client.id")

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
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "naver_client_id", naverClientId)
            resValue("string", "kakao_client_id", kakaoClientId)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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

    implementation(libs.navigation.compose)
    implementation(libs.kotlin.serialization)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.bundles.firebase)

    // Google - Login
    implementation(libs.bundles.google.auth)

    /** Kakao **/
    implementation(libs.kakao.all) // 전체 모듈 설치, 2.11.0 버전부터 지원

    /** Naver **/
    implementation(libs.naver.map)

    implementation(libs.image.cropper)


    //Clean Architecture
    implementation((project(":common")))
    implementation((project(":data")))
    implementation((project(":domain")))
}