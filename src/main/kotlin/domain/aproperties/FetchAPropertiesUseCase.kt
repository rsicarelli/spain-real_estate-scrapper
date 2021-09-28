package domain.aproperties

import data.FirestoreDataSource
import kotlinx.coroutines.*
import mu.KotlinLogging
import data.scrapp.aproperties.APropertiesScrapper
import kotlinx.coroutines.flow.*
import data.scrapp.Scrapper
import domain.MarkAsRemovedUseCase

private val logger = KotlinLogging.logger("FetchAPropertiesUseCase")

class FetchAPropertiesUseCase(
    private val scrapper: Scrapper<APropertiesScrapper.Output>,
    private val firestoreDataSource: FirestoreDataSource,
    private val fetchAPropertyDetailsUseCase: FetchAPropertyDetailsUseCase,
    private val fetchPaginatedPropertiesUseCase: FetchPaginatedAPropertiesUseCase,
    private val markAsRemovedUseCase: MarkAsRemovedUseCase
) {
    @OptIn(FlowPreview::class)
    suspend operator fun invoke() {
        scrapper.scrapSearchPage(DEFAULT_URL, getPagination = true)
            .filter { it.isSuccess }
            .map { it.getOrThrow() as APropertiesScrapper.Output.SearchResult }
            .onEach { logger.info { "Got initial search result: ${it.results.size}" } }
            .flatMapConcat { fetchPaginatedPropertiesUseCase.invoke(it.pagination, it.results) }
            .filter { it.isSuccess }
            .map { it.getOrThrow() }
            .onEach { logger.info { "Got paginated results: ${it.size}" } }
            .flatMapConcat { fetchAPropertyDetailsUseCase.invoke(it) }
            .onEach { logger.info { "Done. Added ${it.properties.size}, not added ${it.failed.size}" } }
            .flatMapConcat { firestoreDataSource.addAll(it.properties, FirestoreDataSource.Type.APROPERTIES) }
            .flatMapConcat { markAsRemovedUseCase.invoke(it, FirestoreDataSource.Type.APROPERTIES) }
            .flowOn(Dispatchers.IO)
            .collect()
    }

    companion object {
        private const val DEFAULT_URL =
            "https://www.aproperties.es/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis="
    }

}