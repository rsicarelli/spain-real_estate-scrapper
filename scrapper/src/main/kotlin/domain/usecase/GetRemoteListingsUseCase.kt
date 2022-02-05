package me.rsicarelli.domain.usecase

import com.google.gson.JsonObject
import domain.entity.Property
import domain.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import mu.KotlinLogging

private val logger = KotlinLogging.logger("GetRemoteListingsUseCase")

class GetRemoteListingsUseCase(
    val propertyRepository: PropertyRepository
) {
    suspend operator fun invoke(request: Request): Flow<List<Property>> {
        val (url, headers, body) = request

        return flow {
            emit(propertyRepository.getProperties(url, headers, body))
        }.catch {
            logger.error { it }
        }
    }

    data class Request(
        val url: String,
        val headers: Map<String, String>,
        val body: JsonObject,
    )
}
