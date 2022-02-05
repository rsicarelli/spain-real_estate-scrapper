package me.rsicarelli.domain.usecase

import com.google.gson.JsonObject
import domain.entity.Property.Type
import domain.usecase.DeleteUnavailablePropertiesUseCase
import domain.usecase.FixPropertiesLocationUseCase
import domain.usecase.SavePropertiesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import mu.KotlinLogging

private val logger = KotlinLogging.logger("ScrapApiRealEstateUseCase")

class ScrapApiRealEstateUseCase(
    private val getRemoteListingsUseCase: GetRemoteListingsUseCase,
    private val saveProperties: SavePropertiesUseCase,
    private val toggleAvailability: DeleteUnavailablePropertiesUseCase,
    private val fixPropertiesLocation: FixPropertiesLocationUseCase
) {
    @OptIn(FlowPreview::class)
    suspend operator fun invoke(request: Request): Flow<Unit> {
        val (url, headers, body, type) = request

        return getRemoteListingsUseCase.invoke(GetRemoteListingsUseCase.Request(url, headers, body))
            .flatMapConcat { fixPropertiesLocation.invoke(FixPropertiesLocationUseCase.Request(it)) }
            .flatMapConcat { saveProperties.invoke(SavePropertiesUseCase.Request(it, type)) }
            .flatMapConcat { toggleAvailability.invoke(DeleteUnavailablePropertiesUseCase.Request(it, type)) }
            .catch { logger.error { it } }
    }

    data class Request(
        val url: String,
        val headers: Map<String, String>,
        val body: JsonObject,
        val realEstateType: Type
    )

}