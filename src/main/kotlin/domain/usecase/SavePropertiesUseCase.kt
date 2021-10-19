package domain.usecase

import domain.entity.Property
import domain.entity.Property.Type
import domain.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SavePropertiesUseCase(
    private val repository: PropertyRepository
) {

    suspend operator fun invoke(request: Request): Flow<List<Property>> {
        return flow {
            val (properties, type) = request

            emit(repository.addAll(properties))
        }
    }

    data class Request(
        val properties: List<Property>,
        val type: Type
    )
}