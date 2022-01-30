plugins {
    id("kotlin-application-convention")
}

dependencies {
    api(libs.bcrypt)
    api(libs.kmongo)
    api(libs.kotlin.reflect)
    api(libs.kotlin.logging)
    api(libs.logback.classic)
    api(libs.bundles.kGraphQL)
    api(libs.bundles.koin)
    api(libs.bundles.ktor)
    api(libs.skrapeIt)
    api(libs.ktor.cio)
    api(libs.okhttp3)
    api(libs.okhttp3.logging)
    api(libs.ktor.serialization)
    api(libs.ktor.client.serialization)
    api(libs.ktor.client.logging)
    api(libs.ktor.gson)
    api(libs.ktor.client.gson)
    println("")

    println()
    testImplementation(libs.bundles.test) {
        exclude("org.jetbrains.kotlin", "kotlin-test-junit")
    }
}
