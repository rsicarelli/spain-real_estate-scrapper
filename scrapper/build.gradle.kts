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

    testImplementation(libs.bundles.test) {
        exclude("org.jetbrains.kotlin", "kotlin-test-junit")
    }
}
