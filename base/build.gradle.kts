@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "kaa.alisherbu.listbook.base"
}

dependencies {
    implementation(libs.dagger.dagger)
    kapt(libs.dagger.compiler)
}
