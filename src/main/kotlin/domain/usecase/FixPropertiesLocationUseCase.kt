package domain.usecase

import domain.model.Property
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FixPropertiesLocationUseCase {

    suspend operator fun invoke(request: Request): Flow<List<Property>> {
        return flow {
            val fixedLocations = request.properties.map { property ->
                if (property.location.isApproximated) {
                    property.copy(location = property.location.fixLocation())
                } else {
                    property
                }
            }
            emit(fixedLocations)
        }
    }

    data class Request(
        val properties: List<Property>
    )
}