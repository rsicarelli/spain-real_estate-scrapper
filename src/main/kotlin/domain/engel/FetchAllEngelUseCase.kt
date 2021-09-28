package domain.engel

import data.FirestoreDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import data.scrapp.Scrapper
import data.scrapp.engelvoelkers.EngelScrapper
import domain.MarkAsRemovedUseCase

private val logger = KotlinLogging.logger("FetchAllEngelUseCase")

class FetchAllEngelUseCase(
    private val scrapper: Scrapper<EngelScrapper.Output>,
    private val paginationUseCase: FetchPaginatedEngelUseCase,
    private val singleUseCase: FetchSingleEngelUseCase,
    private val firestoreDataSource: FirestoreDataSource,
    private val markAsRemovedUseCase: MarkAsRemovedUseCase
) {

    @OptIn(FlowPreview::class)
    suspend operator fun invoke() {
        scrapper.scrapSearchPage(DEFAULT_URL, getPagination = true)
            .map { it as EngelScrapper.Output.SearchResult }
            .onEach { logger.info { "Got initial search result: ${it.results.size}" } }
            .flatMapConcat { paginationUseCase.invoke(it.pagination, it.results) }
            .filter { it.isSuccess }
            .map { it.getOrThrow() }
            .onEach { logger.info { "Got paginated results: ${it.size}" } }
            .flatMapConcat { singleUseCase.invoke(it) }
            .onEach { logger.info { "Done. Added ${it.properties.size}, not added ${it.failed.size}" } }
            .flatMapConcat { firestoreDataSource.addAll(it.properties, FirestoreDataSource.Type.ENGELS) }
            .flatMapConcat { markAsRemovedUseCase.invoke(it, FirestoreDataSource.Type.ENGELS) }
            .flowOn(Dispatchers.IO)
            .collect()
    }

    companion object {
        private const val DEFAULT_URL =
            "https://www.engelvoelkers.com/en/search/?q=&startIndex=0&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=238&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B"
    }
}