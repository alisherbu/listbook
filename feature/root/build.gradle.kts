@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.feature)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.root"
}

dependencies {
    implementation(libs.google.firebase.auth)

    implementation(projects.feature.auth)
    implementation(projects.feature.main)
    implementation(projects.feature.signup)
    implementation(projects.feature.signIn)
    implementation(projects.feature.player)
    implementation(projects.feature.dialog)

    implementation(projects.core.util)
    implementation(projects.core.resource)
    implementation(projects.core.shared)
}
