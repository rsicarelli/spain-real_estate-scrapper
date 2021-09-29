package domain.usecases

import domain.entity.Property.Type
import domain.repositories.PropertyRepository
import domain.valueobjects.PropertySearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import mu.KotlinLogging

private val logger = KotlinLogging.logger("GetFirstSearchPageUseCase")

class GetFirstSearchPageUseCase(
    private val repository: PropertyRepository
) {

    suspend operator fun invoke(request: Request): Flow<PropertySearchResult> {
        val (url, type) = request

        return repository.scrapSearchPage(url, type)
            .onEach {
                logger.info { "Got initial results: ${it.items.size}, total items: ${it.pagination.totalItems} " }
            }
    }

    data class Request(
        val url: String,
        val type: Type
    )
}