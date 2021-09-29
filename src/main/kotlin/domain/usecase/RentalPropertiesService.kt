package app

import domain.model.Property.Type.APROPERTIES
import domain.model.Property.Type.ENGELS
import domain.usecase.ScrapRealEstateUseCase

class RentalPropertiesService(
    private val scrapRealEstate: ScrapRealEstateUseCase
) {
    suspend operator fun invoke() {
        scrapRealEstate.invoke(ScrapRealEstateUseCase.Request(APROPERTIES_DEFAULT_URL, APROPERTIES))
        scrapRealEstate.invoke(ScrapRealEstateUseCase.Request(ENGEL_DEFAULT_URL, ENGELS))
    }

    companion object {
        private const val APROPERTIES_DEFAULT_URL =
            "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=1"

        private const val ENGEL_DEFAULT_URL =
            "https://www.engelvoelkers.com/en/search/?q=&startIndex=0&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=238&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B"
    }
}