@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "kaa.alisherbu.listbook.common.sign_in"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.arkivanov.decompose.decompose)
    implementation(libs.arkivanov.decompose.extensionsComposeJetpack)
    implementation(libs.arkivanov.mvikotlin.mvikotlin)
    implementation(libs.arkivanov.mvikotlin.coroutines)

    implementation(projects.core.util)
    implementation(projects.core.resource)
    implementation(projects.authManager)
    implementation(libs.dagger.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.google.firebase.auth)
}
