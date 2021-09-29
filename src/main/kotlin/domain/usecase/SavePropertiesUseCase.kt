package domain.usecases

import domain.entity.Property
import domain.entity.Property.Type
import domain.repositories.PropertyRepository
import kotlinx.coroutines.flow.Flow

class SavePropertiesUseCase(
    private val repository: PropertyRepository
) {

    suspend operator fun invoke(request: Request): Flow<List<Property>> {
        val (properties, type) = request
        return repository.save(properties, type)
    }

    data class Request(
        val properties: List<Property>,
        val type: Type
    )
}