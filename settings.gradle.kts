@file:Suppress("UnstableApiUsage")

import org.gradle.api.internal.FeaturePreviews.Feature.VERSION_CATALOGS
import org.gradle.api.internal.FeaturePreviews.Feature.TYPESAFE_PROJECT_ACCESSORS

enableFeaturePreview(VERSION_CATALOGS.name)
enableFeaturePreview(TYPESAFE_PROJECT_ACCESSORS.name)

rootProject.name = "home-hunt"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include(":scrapper")