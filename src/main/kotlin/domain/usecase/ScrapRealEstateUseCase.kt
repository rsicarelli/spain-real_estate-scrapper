package domain.usecase

import domain.model.Property.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger("ScrapRealEstateUseCase")

class ScrapRealEstateUseCase(
    private val getFirstResults: GetFirstSearchPageUseCase,
    private val getPaginatedSearchItems: GetPaginatedSearchItemsUseCase,
    private val getProperties: GetPropertyUseCase,
    private val saveProperties: SavePropertiesUseCase,
    private val toggleAvailability: TogglePropertyAvailabilityUseCase
) {
    @OptIn(FlowPreview::class)
    suspend operator fun invoke(request: Request): Flow<Unit> {
        val (url, type) = request

        return getFirstResults.invoke(GetFirstSearchPageUseCase.Request(url, type))
            .flatMapConcat { getPaginatedSearchItems(GetPaginatedSearchItemsUseCase.Request(it, type)) }
            .flatMapConcat { getProperties(GetPropertyUseCase.Request(it, type)) }
            .flatMapConcat { saveProperties.invoke(SavePropertiesUseCase.Request(it, type)) }
            .flatMapConcat { toggleAvailability.invoke(TogglePropertyAvailabilityUseCase.Request(it, type)) }
            .catch { logger.error { it } }
            .flowOn(Dispatchers.IO)
    }

    data class Request(
        val url: String,
        val type: Type
    )

}