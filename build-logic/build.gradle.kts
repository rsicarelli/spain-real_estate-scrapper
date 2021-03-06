import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.gradle)
    implementation(libs.gradle.ksp)
    implementation(libs.kotlin.serialization)
}

tasks.withType(KotlinCompile::class.java).all {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

gradlePlugin {
    plugins.register("kotlin-application-convention") {
        id = "kotlin-application-convention"
        implementationClass = "KotlinApplicationConvention"
    }
}