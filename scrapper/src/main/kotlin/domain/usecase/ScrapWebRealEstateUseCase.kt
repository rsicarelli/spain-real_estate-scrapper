package domain.usecase

import domain.entity.Property.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import me.rsicarelli.domain.usecase.GetRemoteListingsUseCase
import mu.KotlinLogging

private val logger = KotlinLogging.logger("ScrapWebRealEstateUseCase")

class ScrapWebRealEstateUseCase(
    private val getFirstResults: GetFirstSearchPageUseCase,
    private val getPaginatedSearchItems: GetPaginatedSearchItemsUseCase,
    private val getProperties: GetPropertyUseCase,
    private val saveProperties: SavePropertiesUseCase,
    private val toggleAvailability: DeleteUnavailablePropertiesUseCase,
    private val fixPropertiesLocation: FixPropertiesLocationUseCase
) {
    @OptIn(FlowPreview::class)
    suspend operator fun invoke(request: Request): Flow<Unit> {
        val (url, type) = request

        return getFirstResults.invoke(GetFirstSearchPageUseCase.Request(url, type))
            .flatMapConcat { getPaginatedSearchItems(GetPaginatedSearchItemsUseCase.Request(it, type)) }
            .flatMapConcat { getProperties(GetPropertyUseCase.Request(it, type)) }
            .flatMapConcat { fixPropertiesLocation.invoke(FixPropertiesLocationUseCase.Request(it)) }
            .flatMapConcat { saveProperties.invoke(SavePropertiesUseCase.Request(it, type)) }
            .flatMapConcat { toggleAvailability.invoke(DeleteUnavailablePropertiesUseCase.Request(it, type)) }
            .catch { logger.error { it } }
            .flowOn(Dispatchers.IO)
    }

    data class Request(
        val url: String,
        val type: Type
    )

}