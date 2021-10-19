package domain.service

import app.launchPeriodicAsync
import domain.entity.Property
import domain.repository.PropertyRepository
import domain.usecase.ScrapRealEstateUseCase
import domain.valueobject.PropertyItem
import domain.valueobject.PropertyResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import me.rsicarelli.domain.repository.FavouritesRepository
import me.rsicarelli.domain.repository.ViewedPropertiesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, kotlinx.coroutines.InternalCoroutinesApi::class)
class PropertyService : KoinComponent {
    private val repository: PropertyRepository by inject()
    private val viewedPropertiesRepository: ViewedPropertiesRepository by inject()
    private val favouritesRepository: FavouritesRepository by inject()
    private val scrapRealEstate: ScrapRealEstateUseCase by inject()

    init {
        CoroutineScope(Dispatchers.IO).launchPeriodicAsync(Duration.hours(12)) {
            scrapRealEstate.invoke(
                ScrapRealEstateUseCase.Request(
                    APROPERTIES_DEFAULT_URL,
                    Property.Type.APROPERTIES
                )
            ).single()
            scrapRealEstate.invoke(
                ScrapRealEstateUseCase.Request(
                    ENGEL_DEFAULT_URL,
                    Property.Type.ENGELS
                )
            ).single()
        }
    }

    fun getProperties(userId: String): PropertyResponse {
        val properties = repository.getAllActive()
        val favourites = favouritesRepository.getAllByUserId(userId)
        val viewedProperties = viewedPropertiesRepository.getAllByUserId(userId)

        return PropertyResponse(
            properties.map {
                PropertyItem(
                    property = it,
                    isFavourited = favourites?.propertyIds?.contains(it._id) ?: false,
                    isViewed = viewedProperties?.propertyIds?.contains(it._id) ?: false
                )
            },
            totalItems = properties.size
        )
    }

    fun getProperty(id: String): Property {
        return repository.getById(id)
    }

    companion object {
        private const val APROPERTIES_DEFAULT_URL =
            "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=1"

        private const val ENGEL_DEFAULT_URL =
            "https://www.engelvoelkers.com/en/search/?q=&startIndex=0&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=238&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B"
    }
}
