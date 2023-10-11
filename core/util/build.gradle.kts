@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "kaa.alisherbu.listbook.core.util"
    compileSdk = libs.versions.compileSdk.get().toInt()
}

dependencies {
    implementation(libs.androidx.compose.material3)
}
