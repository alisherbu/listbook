@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.listbook.android.dagger)
}

android {
    namespace = "kaa.alisherbu.listbook.core.database"
}

dependencies {
    implementation(libs.google.firebase.firestore)
    implementation(libs.exoplayer)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.shared)
}
