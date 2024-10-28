// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.kotlin.ksp) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.android.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}