package me.rsicarelli.data.datasource

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*

interface RemoteDataSource {
    val client: HttpClient
}

class RemoteDataSourceImpl : RemoteDataSource {
    override val client: HttpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }
}
