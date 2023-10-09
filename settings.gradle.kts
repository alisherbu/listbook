@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":auth-manager")
include(":core:util")
include(":core:resource")
include(":common:root")
include(":common:auth")
include(":common:home")
include(":common:signup")
include(":common:sign-in")
include(":common:dialog")

pluginManagement {
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
