package domain.usecases

import domain.entity.Property
import domain.entity.Property.Type
import domain.repositories.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import mu.KotlinLogging


private val logger = KotlinLogging.logger("TogglePropertyAvailabilityUseCase")

class TogglePropertyAvailabilityUseCase(
    private val repository: PropertyRepository
) {

    @OptIn(FlowPreview::class)
    operator fun invoke(request: Request): Flow<Unit> {
        val (newProperties, type) = request

        return repository.getAll(type)
            .map { cachedProperties ->
                val first = cachedProperties.map { it.reference }
                val second = newProperties.map { it.reference }
                Pair(first.minus(second), second)
            }
            .flatMapConcat { repository.markAvailability(it.first, it.second, type) }
            .flowOn(Dispatchers.IO)
    }

    data class Request(
        val newProperties: List<Property>,
        val type: Type
    )
}