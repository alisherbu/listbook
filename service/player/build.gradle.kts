@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.listbook.android.dagger)
}

android {
    namespace = "kaa.alisherbu.listbook.service.player"
}

dependencies {
    implementation(libs.exoplayer)
    implementation(projects.core.shared)
    implementation(projects.core.domain)
    implementation(libs.timber)
    implementation(libs.coroutines.android)
}
