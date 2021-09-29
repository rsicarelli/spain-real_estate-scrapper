package domain.usecase

import domain.model.Property.Type.APROPERTIES
import domain.model.Property.Type.ENGELS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RentalPropertiesService(
    private val scrapRealEstate: ScrapRealEstateUseCase
) {

    private val scope = CoroutineScope(Dispatchers.Default)

    suspend operator fun invoke() {
        scrapRealEstate.invoke(ScrapRealEstateUseCase.Request(APROPERTIES_DEFAULT_URL, APROPERTIES)).collect()
        scrapRealEstate.invoke(ScrapRealEstateUseCase.Request(ENGEL_DEFAULT_URL, ENGELS)).collect()
    }

    companion object {
        private const val APROPERTIES_DEFAULT_URL =
            "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=1"

        private const val ENGEL_DEFAULT_URL =
            "https://www.engelvoelkers.com/en/search/?q=&startIndex=0&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=238&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B"
    }
}