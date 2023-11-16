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

/*
Allows to run detekt for all files in the Gradle project
and all subprojects without a need to configure detekt plugin in every subproject.
 */
tasks.register("detektCheck", Detekt::class) {
    parallel = true
    setSource(file(projectDir))

    config.setFrom("$projectDir/config/detekt/detekt.yml")

    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**", "**/generated/**", "**/.gradle/**")
    dependencies {
        detektPlugins(libs.detekt.formatting)
        detektPlugins(libs.detekt.compose)
        detektPlugins(libs.detekt.ruleauthors)
    }
}

tasks.register("clean").configure {
    delete("build")
}

tasks.register("copyGitHooks", Copy::class.java) {
    description = "Copies the git hooks from /config/hooks to the .git folder."
    group = "git hooks"
    from("$rootDir/config/hooks/pre-push")
    into("$rootDir/.git/hooks/")
}

tasks.register("installGitHooks", Exec::class.java) {
    description = "Installs the pre-commit git hooks from /git-hooks."
    group = "git hooks"
    workingDir = rootDir
    commandLine = listOf("chmod")
    args("-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    doLast {
        logger.info("Git hook installed successfully.")
    }
}

afterEvaluate {
    tasks.getByPath(":app:preBuild").dependsOn(":installGitHooks")
}
