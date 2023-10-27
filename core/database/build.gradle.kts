@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
    alias(libs.plugins.listbook.android.dagger)
    alias(libs.plugins.google.ksp)
}

android {
    namespace = "kaa.alisherbu.listbook.core.data"
}

dependencies {
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.coroutines.core)
    ksp(libs.room.compiler)
}
