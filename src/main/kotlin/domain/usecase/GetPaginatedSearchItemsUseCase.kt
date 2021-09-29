package domain.usecases

import domain.entity.Property.Type
import domain.repositories.PropertyRepository
import domain.valueobjects.PropertyItem
import domain.valueobjects.PropertySearchResult
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