@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:util")
include(":core:shared")
include(":core:resource")
include(":feature:root")
include(":feature:main")
include(":feature:auth")
include(":feature:home")
include(":feature:signup")
include(":feature:sign-in")
include(":feature:dialog")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "listbook"
