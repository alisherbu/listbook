@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.listbook.android.dagger)
}

android {
    namespace = "kaa.alisherbu.listbook.core.domain"
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(projects.core.shared)
}
