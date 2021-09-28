package domain.aproperties

import domain.valueobjects.APropertiesPagination
import domain.valueobjects.PropertySearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mu.KotlinLogging
import data.scrapp.Scrapper
import data.scrapp.aproperties.APropertiesScrapper

private val logger = KotlinLogging.logger("FetchPaginatedPropertiesUseCase")

class FetchPaginatedAPropertiesUseCase(
    private val aPropertiesScrapper: Scrapper<APropertiesScrapper.Output>,
) {

    suspend operator fun invoke(
        paginationMeta: APropertiesPagination?,
        initialResults: List<PropertySearchResult>
    ): Flow<Result<List<PropertySearchResult>>> {
        return flow<Result<List<PropertySearchResult>>> {
            if (paginationMeta == null || paginationMeta.pageCount <= 1) {
                emit(Result.failure(RuntimeException("Skipping pagination $paginationMeta")))
            } else {
                val combinedResults = ArrayList(initialResults)
                //skipping first page since it was already parsed
                for (i in 2..paginationMeta.pageCount) {
                    val pagination = paginationMeta.getPagination(i)
                    aPropertiesScrapper.scrapSearchPage(pagination, false)
                        .collect {
                            when (it) {
                                is APropertiesScrapper.Output.SearchResult -> combinedResults.addAll(it.results)
                            }
                        }
                }
                emit(Result.success(combinedResults))
            }
        }.flowOn(Dispatchers.IO)
    }
}