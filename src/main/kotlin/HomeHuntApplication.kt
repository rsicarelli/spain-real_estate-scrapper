package me.rsicarelli

import app.AppInitializer
import app.launchPeriodicAsync
import com.apurebase.kgraphql.GraphQL
import data.graphql.propertySchema
import di.appModules
import domain.service.PropertyService
import domain.service.RentalPropertiesService
import io.ktor.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class HomeHuntApplication : KoinComponent {
    val service: RentalPropertiesService by inject()
    val propertyService: PropertyService by inject()
    val appInitializer: AppInitializer by inject()
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    startKoin {
        printLogger()
        modules(appModules)
    }

    val app = HomeHuntApplication()
    app.appInitializer.invoke()

    install(GraphQL) {
        playground = true
//        context { call ->
//            authService.verifyToken(call)?.let { +it }
//            +log
//            +call
//        }

        schema {
            propertySchema(app.propertyService)
        }
    }
}

//fun main() {
//
//
//    val app = HomeHuntApplication()
//
//    val executionTime = measureDuration {
//        with(app) {
//            appInitializer.invoke()
//            runBlocking { service.invoke() }
//        }
//    }
//    println("Finish. Took $executionTime")
//}