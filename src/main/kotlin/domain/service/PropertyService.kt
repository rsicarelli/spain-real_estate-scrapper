package domain.service

import domain.entity.Property
import domain.repository.PropertyRepository
import domain.valueobject.PropertyResponse
import me.rsicarelli.domain.repository.RatingsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PropertyService : KoinComponent {
    private val repository: PropertyRepository by inject()
    private val ratingsRepository: RatingsRepository by inject()

    fun getProperties(userId: String): PropertyResponse {
        val properties = repository.getAll()
        val ratings = ratingsRepository.getAllByUserId(userId) //TODO refactor to Mongo aggregates

        val filteredProperties = properties.filterNot { property ->
            ratings?.let {
                property._id in it.downVotedProperties
            } ?: true
        }

        return PropertyResponse(
            count = filteredProperties.size,
            results = filteredProperties,
        )
    }

    fun getProperty(id: String): Property {
        return repository.getById(id)
    }

    fun getRecommendations(userId: String): PropertyResponse {
        val properties = repository.getAll()
        val ratings = ratingsRepository.getAllByUserId(userId) //TODO refactor to Mongo aggregates

        val filteredProperties = properties.filterNot { property ->
            ratings?.let {
                property._id in it.downVotedProperties || property._id in it.upVotedProperties
            } ?: true
        }

        return PropertyResponse(
            count = filteredProperties.size,
            results = filteredProperties,
        )
    }
}

