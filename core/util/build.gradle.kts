@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "kaa.alisherbu.listbook.core.util"
}

dependencies {
    implementation(libs.androidx.compose.material3)
}
