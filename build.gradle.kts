buildscript {
    repositories {
        mavenCentral()
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}

tasks.create("stage") {
    dependsOn("installDist")
}