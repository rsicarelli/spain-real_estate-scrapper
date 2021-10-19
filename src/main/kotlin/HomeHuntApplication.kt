package me.rsicarelli

import app.AppInitializer
import com.apurebase.kgraphql.GraphQL
import data.graphql.propertySchema
import di.appModules
import domain.service.PropertyService
import domain.service.RentalPropertiesService
import io.ktor.application.*
import me.rsicarelli.data.graphql.authSchema
import me.rsicarelli.data.graphql.favouritesSchema
import me.rsicarelli.domain.service.AuthService
import me.rsicarelli.domain.service.FavouritesService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class HomeHuntApplication : KoinComponent {
    val service: RentalPropertiesService by inject()
    val propertyService: PropertyService by inject()
    val appInitializer: AppInitializer by inject()
    val authService: AuthService by inject()
    val favouriteService: FavouritesService by inject()
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

        context { call ->
            app.authService.verifyToken(call)?.let { +it }
            +log
            +call
        }

        schema {
            propertySchema(app.propertyService)
            authSchema(app.authService)
            favouritesSchema(app.favouriteService)
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