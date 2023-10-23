@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.feature)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.signup"
}

dependencies {
    implementation(libs.google.firebase.auth)

    implementation(projects.core.util)
    implementation(projects.core.shared)
    implementation(projects.core.resource)
}
