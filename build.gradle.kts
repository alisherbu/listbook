@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}

allprojects {
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)

    detekt {
        parallel = true
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        ignoredBuildTypes = listOf("release")
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            xml.required = false
            html.required = false
            txt.required = false
            sarif.required = false
            md.required = false
        }
    }

    tasks.register<Detekt>("detektFormat") {
        autoCorrect = true
        parallel = true
        ignoreFailures = true
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        setSource(file(projectDir))
    }

    dependencies {
        detektPlugins(rootProject.libs.detekt.formatting)
        detektPlugins(rootProject.libs.detekt.compose)
    }
}

tasks.register("clean").configure {
    delete("build")
}
