@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.library)
}

android {
    namespace = "kaa.alisherbu.listbook.core.util"
}

dependencies {
    implementation(libs.androidx.compose.material3)
}
