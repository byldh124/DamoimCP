@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.android.kotlin.ksp)
}

android {
    namespace ="com.moondroid.damoim.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    //retrofit
    implementation(libs.bundles.squareup)

    // Room
    implementation(libs.bundles.room)
    implementation(libs.room.testing)
    //kapt -> ksp migration https://kotlinlang.org/docs/ksp-overview.html#supported-libraries
    ksp(libs.room.compiler)

    //hilt
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // clean architecture : project struct
    implementation((project(":domain")))
    implementation((project(":common")))
}