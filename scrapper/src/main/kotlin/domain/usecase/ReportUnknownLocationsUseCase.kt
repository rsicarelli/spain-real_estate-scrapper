package domain.usecase

import domain.repository.PropertyRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class ReportUnknownLocationsUseCase(
    val propertyRepository: PropertyRepository
) {

    @OptIn(FlowPreview::class)
    suspend operator fun invoke(): Flow<Unit> {
        //TODO: refactor after update
        return flow { emit(Unit) }
//        return propertyRepository.getAll()
//            .filter { cachedProperties ->
//                cachedProperties.location.isUnknown
//            }.map {
//                it.location
//            }
//            .flatMapConcat {
//                propertyRepository.saveUnknownLocations(it)
//            }
    }

}