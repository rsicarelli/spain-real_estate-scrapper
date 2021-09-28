object Dependencies {
    private const val skrapeItVersion = "1.1.5"
    const val skrapeIt = "it.skrape:skrapeit:${skrapeItVersion}"

    private const val firestoreAdminVersion = "8.0.1"
    const val firestoreAdmin = "com.google.firebase:firebase-admin:${firestoreAdminVersion}"

    private const val kotlinLoggingVersion = "1.5.9"
    const val kotlinLogging = "io.github.microutils:kotlin-logging:${kotlinLoggingVersion}"

    private const val koinVersion = "3.1.2"
    const val koin = "io.insert-koin:koin-core:$koinVersion"
    const val koinTest = "io.insert-koin:koin-test:$koinVersion"
    const val koinTestJUnit5 = "io.insert-koin:koin-test-junit5:$koinVersion"

    private const val jUnit5Version = "5.7.0"
    const val jUnit5 = "org.junit.jupiter:junit-jupiter:$jUnit5Version"

    private const val mokkVersion = "1.12.0"
    const val mokk = "io.mockk:mockk:$mokkVersion"
}

object Kotlin {
    const val version = "1.5.21"
    const val test = "test-junit5"
}