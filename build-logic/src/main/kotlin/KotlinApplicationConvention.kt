import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.application
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

open class KotlinApplicationConvention : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            this.plugins.apply("application")
            this.plugins.apply("org.jetbrains.kotlin.jvm")
            extensions.configure(JavaPluginExtension::class.java) {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
            tasks.withType(KotlinCompile::class.java).all {
                kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
            }

            (extensions.getByName("application") as org.gradle.api.plugins.JavaApplication).run {
                mainClassName = "io.ktor.server.netty.EngineMain"
            }
            tasks.withType(Test::class.java).configureEach {
                useJUnitPlatform()
            }
            tasks.withType(Jar::class.java) {
                manifest {
                    manifest {
                        attributes(
                            mapOf(
                                "Main-Class" to "io.ktor.server.netty.EngineMain"
                            )
                        )
                    }
                }
            }

            (extensions.getByName("sourceSets") as org.gradle.api.tasks.SourceSetContainer).run {
                getByName("main").resources.srcDirs("resources")
            }

            group = "me.rsicarelli"
            version = "1.0-SNAPSHOT"
        }
    }
}
