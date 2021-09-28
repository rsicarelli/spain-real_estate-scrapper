package domain.engel

import domain.valueobjects.EngelPagination
import domain.valueobjects.PropertySearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mu.KotlinLogging
import data.scrapp.Scrapper
import data.scrapp.engelvoelkers.EngelScrapper

private val logger = KotlinLogging.logger("FetchPaginatedPropertiesUseCase")

class FetchPaginatedEngelUseCase(
    private val scrapper: Scrapper<EngelScrapper.Output>
) {

    suspend operator fun invoke(
        pagination: EngelPagination?,
        initialResults: List<PropertySearchResult>
    ): Flow<Result<List<PropertySearchResult>>> {

        return flow {
            if (pagination == null || pagination.pageCount <= 1) {
                emit(Result.failure(RuntimeException("Skipping pagination $pagination")))
            } else {
                val combinedResults = ArrayList(initialResults)
                pagination.getPagination().forEach { url ->
                    scrapper.scrapSearchPage(url, false)
                        .collect {
                            if (it.isSuccess) {
                                when (val output = it.getOrNull()) {
                                    is EngelScrapper.Output.SearchResult -> combinedResults.addAll(output.results)
                                }
                            } else {
                                logger.error { "Failed to scrap $pagination" }
                            }
                        }
                }
                emit(Result.success(combinedResults))
            }
        }.flowOn(Dispatchers.IO)
    }

}