package domain.service

import domain.entity.Property
import domain.repository.PropertyRepository
import domain.valueobject.PropertyItem
import domain.valueobject.PropertyResponse
import me.rsicarelli.domain.repository.RatingsRepository
import me.rsicarelli.domain.repository.ViewedPropertiesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PropertyService : KoinComponent {
    private val repository: PropertyRepository by inject()
    private val viewedPropertiesRepository: ViewedPropertiesRepository by inject()
    private val ratingsRepository: RatingsRepository by inject()

    fun getProperties(userId: String): PropertyResponse {
        val properties = repository.getAll()
        val ratings = ratingsRepository.getAllByUserId(userId)
        val viewedProperties = viewedPropertiesRepository.getAllByUserId(userId)

        return PropertyResponse(
            properties.map {
                PropertyItem(
                    property = it,
                    isUpVoted = ratings?.upVotedProperties?.contains(it._id) ?: false,
                    isDownVoted = ratings?.downVotedProperties?.contains(it._id) ?: false,
                    isViewed = viewedProperties?.propertyIds?.contains(it._id) ?: false
                )
            },
            totalItems = properties.size
        )
    }

    fun getProperty(id: String): Property {
        return repository.getById(id)
    }
}
