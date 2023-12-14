@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.feature)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.player"
}
dependencies {

    implementation(projects.core.shared)
    implementation(projects.service.player)
    implementation(projects.feature.chapter)
}
