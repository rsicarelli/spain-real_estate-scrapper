package app

import domain.MarkAsRemovedUseCase
import domain.aproperties.FetchAPropertiesUseCase
import domain.engel.FetchAllEngelUseCase

class PropertiesScrapper(
    private val fetchAPropertiesUseCase: FetchAPropertiesUseCase,
    private val fetchAllEngelUseCase: FetchAllEngelUseCase,
    val markAsRemovedUseCase: MarkAsRemovedUseCase
) {
    suspend operator fun invoke() {
        fetchAPropertiesUseCase.invoke()
        fetchAllEngelUseCase.invoke()
        //            markAsRemovedUseCase.invoke(result)
    }
}