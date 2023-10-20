@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage")

plugins {
    alias(libs.plugins.listbook.android.application)
    alias(libs.plugins.listbook.android.dagger)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.services)
}

android {
    namespace = "kaa.alisherbu.listbook"

    defaultConfig {
        applicationId = "kaa.alisherbu.listbook"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.arkivanov.decompose.decompose)
    implementation(libs.arkivanov.decompose.extensionsComposeJetpack)
    implementation(libs.arkivanov.mvikotlin.mvikotlin)
    implementation(libs.arkivanov.mvikotlin.logging)
    implementation(libs.arkivanov.mvikotlin.main)
    implementation(libs.google.firebase.core)
    implementation(libs.google.firebase.auth)

    implementation(projects.core.util)
    implementation(projects.feature.root)
    implementation(projects.feature.auth)
    implementation(projects.feature.signIn)
    implementation(projects.feature.signup)
    implementation(projects.feature.main)
    implementation(projects.feature.home)

}
