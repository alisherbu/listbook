@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:util")
include(":core:shared")
include(":core:resource")
include(":core:domain")
include(":core:data")
include(":core:model")
include(":core:database")
include(":service:player")
include(":feature:root")
include(":feature:main")
include(":feature:auth")
include(":feature:home")
include(":feature:profile")
include(":feature:signup")
include(":feature:sign-in")
include(":feature:dialog")
include(":feature:player")

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
