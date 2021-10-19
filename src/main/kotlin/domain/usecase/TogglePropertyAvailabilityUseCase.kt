package domain.usecase

import domain.entity.Property
import domain.entity.Property.Type
import domain.repository.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class TogglePropertyAvailabilityUseCase(
    private val repository: PropertyRepository
) {

    @OptIn(FlowPreview::class)
    suspend operator fun invoke(request: Request): Flow<Unit> {
        val (newProperties, type) = request

        return repository.getAllFromType(type)
            .map { cachedProperties ->
                val first = cachedProperties.map { it._id }
                val second = newProperties.map { it._id }
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