@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "kaa.alisherbu.listbook.common.root"
    compileSdk = libs.versions.compileSdk.get().toInt()
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.arkivanov.decompose.decompose)
    implementation(libs.arkivanov.decompose.extensionsComposeJetpack)
    implementation(libs.arkivanov.mvikotlin.mvikotlin)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    implementation(projects.common.auth)
    implementation(projects.common.home)
    implementation(projects.common.signup)

    implementation(projects.core.util)
    implementation(projects.core.resource)
}
