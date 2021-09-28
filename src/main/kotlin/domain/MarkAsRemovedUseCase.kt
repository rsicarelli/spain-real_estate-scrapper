package domain

import data.FirestoreDataSource
import domain.entity.Property
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import mu.KotlinLogging


private val logger = KotlinLogging.logger("MarkAsRemovedUseCase")

class MarkAsRemovedUseCase(
    private val firestoreDataSource: FirestoreDataSource
) {

    @OptIn(FlowPreview::class)
    operator fun invoke(newProperties: List<Property>, type: FirestoreDataSource.Type): Flow<Unit> {
        return firestoreDataSource.getAll(type)
            .map { cachedProperties ->
                val first = cachedProperties.map { it.reference }
                val second = newProperties.map { it.reference }
                Pair(first.minus(second), second)
            }
            .flatMapConcat { firestoreDataSource.markAvailability(it.first, it.second, type) }
            .flowOn(Dispatchers.IO)
            .onCompletion { logger.info { "Toggled property status" } }
    }
}