@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.feature)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.home"
}

dependencies {
    implementation(libs.google.firebase.firestore)

    implementation(projects.core.shared)
    implementation(projects.core.domain)
    implementation(projects.service.player)
}
