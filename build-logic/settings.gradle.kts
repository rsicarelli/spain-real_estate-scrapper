@file:Suppress("UnstableApiUsage")

import org.gradle.api.internal.FeaturePreviews.Feature.VERSION_CATALOGS

enableFeaturePreview(VERSION_CATALOGS.name)

rootProject.name = "build-logic"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}