@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "kaa.alisherbu.listbook.common.root"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
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
    implementation(projects.common.signIn)
    implementation(projects.common.dialog)

    implementation(projects.core.util)
    implementation(projects.core.resource)
}
