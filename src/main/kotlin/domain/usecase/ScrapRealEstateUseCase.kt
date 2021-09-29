package domain.usecases

import domain.entity.Property.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
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
    suspend operator fun invoke(request: Request) {
        val (url, type) = request

        getFirstResults.invoke(GetFirstSearchPageUseCase.Request(url, type))
            .flatMapConcat { getPaginatedSearchItems(GetPaginatedSearchItemsUseCase.Request(it, type)) }
            .flatMapConcat { getProperties(GetPropertyUseCase.Request(it, type)) }
            .flatMapConcat { saveProperties.invoke(SavePropertiesUseCase.Request(it, type)) }
            .flatMapConcat { toggleAvailability.invoke(TogglePropertyAvailabilityUseCase.Request(it, type)) }
            .flowOn(Dispatchers.IO)
            .onCompletion { logger.info { "Done scrapping ${type.tag}" } }
    }

    data class Request(
        val url: String,
        val type: Type
    )



}