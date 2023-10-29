@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.feature)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.chapter"
}
dependencies {
    implementation(projects.core.util)
    implementation(projects.core.resource)
    implementation(projects.core.shared)
    implementation(projects.core.database)
}
