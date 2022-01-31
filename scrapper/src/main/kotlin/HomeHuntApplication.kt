package me.rsicarelli

import com.apurebase.kgraphql.GraphQL
import data.graphql.propertySchema
import di.appModules
import domain.service.PropertyService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.serialization.*
import me.rsicarelli.data.graphql.authSchema
import me.rsicarelli.data.graphql.ratingsSchema
import me.rsicarelli.data.graphql.viewedPropertiesSchema
import me.rsicarelli.domain.service.AuthService
import me.rsicarelli.domain.service.RatingsService
import me.rsicarelli.domain.service.ScrapperService
import me.rsicarelli.domain.service.ViewedPropertiesService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import org.koin.ktor.ext.Koin

class HomeHuntApplication : KoinComponent {
    val scrapperService: ScrapperService by inject()
    val propertyService: PropertyService by inject()
    val authService: AuthService by inject()
    val ratingsService: RatingsService by inject()
    val viewedPropertiesService: ViewedPropertiesService by inject()
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

class ErrorLogger : Logger(Level.ERROR) {
    override fun log(level: Level, msg: MESSAGE) {
        println(msg)
    }
}

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Koin) {
        logger(ErrorLogger())
        modules(appModules)
    }

    val app = HomeHuntApplication()
    app.scrapperService.invoke()

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
            ratingsSchema(app.ratingsService)
            viewedPropertiesSchema(app.viewedPropertiesService)
        }
    }

    install(ContentNegotiation) {
        gson()
    }
}
