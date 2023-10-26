@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.listbook.android.dagger)
}

android {
    namespace = "kaa.alisherbu.listbook.core.player"
}

dependencies {
    implementation(libs.exoplayer)
    implementation(projects.core.shared)
    implementation(libs.timber)
    implementation(libs.coroutines.android)
}
