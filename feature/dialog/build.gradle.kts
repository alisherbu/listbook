@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.listbook.android.library.compose)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.dialog"
}
dependencies {
    implementation(libs.arkivanov.mvikotlin.mvikotlin)
    implementation(libs.arkivanov.mvikotlin.coroutines)
    implementation(libs.arkivanov.decompose.decompose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    implementation(projects.core.util)
    implementation(projects.core.resource)
}
