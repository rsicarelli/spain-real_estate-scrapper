package domain.usecase

import domain.model.Property.Type
import domain.repository.PropertyRepository
import domain.model.PropertySearchResult
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