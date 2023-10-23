@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.feature)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.main"
}

dependencies {
    implementation(libs.androidx.compose.material)

    implementation(projects.feature.home)
    implementation(projects.feature.profile)
    implementation(projects.core.shared)
}