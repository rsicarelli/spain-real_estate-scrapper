package me.rsicarelli

import com.apurebase.kgraphql.GraphQL
import data.graphql.propertySchema
import di.appModules
import domain.service.PropertyService
import io.ktor.application.*
import me.rsicarelli.data.graphql.authSchema
import me.rsicarelli.data.graphql.favouritesSchema
import me.rsicarelli.data.graphql.viewedPropertiesSchema
import me.rsicarelli.domain.service.AuthService
import me.rsicarelli.domain.service.FavouritesService
import me.rsicarelli.domain.service.ViewedPropertiesService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class HomeHuntApplication : KoinComponent {
    val propertyService: PropertyService by inject()
    val authService: AuthService by inject()
    val favouriteService: FavouritesService by inject()
    val viewedPropertiesService: ViewedPropertiesService by inject()
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
            viewedPropertiesSchema(app.viewedPropertiesService)
        }
    }
}
