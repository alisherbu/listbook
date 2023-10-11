@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "kaa.alisherbu.listbook.common.dialog"
    compileSdk = libs.versions.compileSdk.get().toInt()
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}
dependencies {
    implementation(libs.arkivanov.mvikotlin.mvikotlin)
    implementation(libs.arkivanov.mvikotlin.coroutines)
    implementation(libs.arkivanov.decompose.decompose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.koin.core)

    implementation(projects.core.util)
    implementation(projects.core.resource)
}
