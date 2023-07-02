@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "kaa.alisherbu.listbook.common.root"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

dependencies {
    implementation(libs.arkivanov.decompose.decompose)
    implementation(libs.arkivanov.mvikotlin.mvikotlin)

    implementation(projects.common.auth)
    implementation(projects.core.util)
}
