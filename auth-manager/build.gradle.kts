@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "kaa.alisherbu.listbook.auth_manager"
}

dependencies {
    implementation(libs.google.firebase.auth)
    implementation(libs.koin.core)
}
