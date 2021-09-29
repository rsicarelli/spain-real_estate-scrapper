package domain.usecase

import domain.model.Property.Type
import domain.repository.PropertyRepository
import domain.model.PropertyItem
import domain.model.PropertySearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mu.KotlinLogging

private val logger = KotlinLogging.logger("GetPaginatedSearchItemsUseCase")

class GetPaginatedSearchItemsUseCase(
    private val propertyRepository: PropertyRepository
) {
    suspend operator fun invoke(request: Request): Flow<List<PropertyItem>> {
        val (initialResults, type) = request

        return flow {
            if (initialResults.pagination.pagesUrl.isEmpty()) {
                emit(initialResults.items)
            } else {

                logger.info { "Getting paginated results. Page size: ${initialResults.pagination.pagesUrl.size}" }
                val combinedResults = ArrayList(initialResults.items)
                initialResults.pagination.pagesUrl.forEach { propertyUrl ->
                    propertyRepository.scrapSearchPage(propertyUrl, type)
                        .collect { searchResult ->
                            combinedResults.addAll(searchResult.items)
                        }
                }
                logger.info { "Got paginated results: ${combinedResults.size}" }
                emit(combinedResults)
            }
        }.flowOn(Dispatchers.IO)
    }

    data class Request(
        val initialResults: PropertySearchResult,
        val type: Type
    )
}