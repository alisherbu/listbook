@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.feature)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.sign_in"
}

dependencies {
    
    implementation(projects.core.shared)
    implementation(projects.core.resource)
    implementation(libs.google.firebase.auth)
}
