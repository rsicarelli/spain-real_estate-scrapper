import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Kotlin.version
    id(Plugins.gradleShadow) version Plugins.gradleShadowVersion
}

group = "me.rsicarelli"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(Bcrypt.bCrypt)

    implementation(Firebase.firestoreAdmin)

    implementation(KGraphQl.kGraphQL)
    implementation(KGraphQl.kGraphQLKtor)

    implementation(KMongo.kMongo)

    implementation(Koin.koin)
    implementation(Koin.koinKtor)

    implementation(Kotlin.reflection)

    implementation(KotlinLogging.kotlinLogging)

    implementation(Ktor.ktorAuth)
    implementation(Ktor.ktorAuthJwt)
    implementation(Ktor.ktorNetty)

    implementation(Logback.logback)

    implementation(SkrapeIt.skrapeIt)

    testImplementation(JUnit5.jUnit5)
    testImplementation(Mokk.mokk)
    testImplementation(kotlin(Kotlin.test))
    testImplementation(Koin.koinTest) {
        exclude("org.jetbrains.kotlin", "kotlin-test-junit")
    }
    testImplementation(Koin.koinTestJUnit5) {
        exclude("org.jetbrains.kotlin", "kotlin-test-junit")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}
