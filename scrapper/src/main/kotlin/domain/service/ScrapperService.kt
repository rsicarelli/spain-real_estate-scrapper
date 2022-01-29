package me.rsicarelli.domain.service

import app.launchPeriodicAsync
import domain.entity.Property
import domain.usecase.ScrapRealEstateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, InternalCoroutinesApi::class)
class ScrapperService : KoinComponent {
    private val scrapRealEstate: ScrapRealEstateUseCase by inject()

    operator fun invoke() {
        CoroutineScope(Dispatchers.IO).launchPeriodicAsync(Duration.hours(6)) {
            scrapRealEstate.invoke(
                ScrapRealEstateUseCase.Request(
                    APROPERTIES_DEFAULT_URL,
                    Property.Type.APROPERTIES
                )
            ).collect()
            scrapRealEstate.invoke(
                ScrapRealEstateUseCase.Request(
                    ENGEL_DEFAULT_URL,
                    Property.Type.ENGELS
                )
            ).collect()
        }
    }

    companion object {
        private const val APROPERTIES_DEFAULT_URL =
            "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=1"

        private const val ENGEL_DEFAULT_URL =
            "https://www.engelvoelkers.com/en/search/?q=&startIndex=0&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=238&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B"
    }
}