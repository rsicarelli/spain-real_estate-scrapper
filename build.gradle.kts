import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Kotlin.version
}

group = "me.rsicarelli"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(Dependencies.skrapeIt)
    implementation(Dependencies.firestoreAdmin)
    implementation(Dependencies.kotlinLogging)
    implementation(Dependencies.koin)
    testImplementation(Dependencies.jUnit5)
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}