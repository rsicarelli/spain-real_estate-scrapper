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
    testImplementation(kotlin(Kotlin.test))
    testImplementation(Dependencies.koinTest) {
        exclude("org.jetbrains.kotlin", "kotlin-test-junit")
    }
    testImplementation(Dependencies.koinTestJUnit5) {
        exclude("org.jetbrains.kotlin", "kotlin-test-junit")
    }
    testImplementation(Dependencies.mokk)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

