package app

import di.appModules
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.time.measureDuration
import java.util.concurrent.TimeUnit


class HomeHuntApplication : KoinComponent {
    val propertiesScrapper: PropertiesScrapper by inject()
    val appInitializer: AppInitializer by inject()
}

fun main() {
    startKoin {
        printLogger()
        modules(appModules)
    }

    val app = HomeHuntApplication()

    measureDuration {
        runBlocking {
            with(app) {
                appInitializer()
                app.propertiesScrapper()
            }
        }
    }
}