package domain.usecase

import domain.model.Property
import domain.model.Property.Type
import domain.repository.PropertyRepository
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