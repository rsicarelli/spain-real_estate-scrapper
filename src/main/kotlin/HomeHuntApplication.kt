import app.AppInitializer
import di.appModules
import domain.service.RentalPropertiesService
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.time.measureDuration


class HomeHuntApplication : KoinComponent {
    val service: RentalPropertiesService by inject()
    val appInitializer: AppInitializer by inject()
}

fun main() {
    startKoin {
        printLogger()
        modules(appModules)
    }

    val app = HomeHuntApplication()

    val executionTime = measureDuration {
        with(app) {
            appInitializer.invoke()
            runBlocking { service.invoke() }
        }
    }
    println("Finish. Took $executionTime")
}