@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.feature)
}

android {
    namespace = "kaa.alisherbu.listbook.feature.auth"
}
dependencies {
    
    implementation(projects.core.shared)
    
}
