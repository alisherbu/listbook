@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.listbook.android.library.compose)
    alias(libs.plugins.listbook.android.dagger)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.sign_in"
}

dependencies {
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.arkivanov.decompose.decompose)
    implementation(libs.arkivanov.decompose.extensionsComposeJetpack)
    implementation(libs.arkivanov.mvikotlin.mvikotlin)
    implementation(libs.arkivanov.mvikotlin.coroutines)

    implementation(projects.core.util)
    implementation(projects.core.shared)
    implementation(projects.core.resource)
    implementation(libs.google.firebase.auth)
}
