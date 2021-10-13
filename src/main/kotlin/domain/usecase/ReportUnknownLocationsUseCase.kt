package domain.usecase

import domain.repository.PropertyRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class ReportUnknownLocationsUseCase(
    val propertyRepository: PropertyRepository
) {

    @OptIn(FlowPreview::class)
    suspend operator fun invoke(): Flow<Unit> {
        return propertyRepository.getAll()
            .map { cachedProperties ->
                cachedProperties.filter { it.location.isUnknown }.map { it.location }
            }.flatMapConcat {
                propertyRepository.saveUnknownLocations(it)
            }
    }

}