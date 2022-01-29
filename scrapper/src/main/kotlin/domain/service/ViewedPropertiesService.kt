package me.rsicarelli.domain.service

import me.rsicarelli.domain.entity.ViewedProperties
import me.rsicarelli.domain.repository.UserRepository
import me.rsicarelli.domain.repository.ViewedPropertiesRepository
import me.rsicarelli.domain.valueobject.ViewedPropertiesResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ViewedPropertiesService : KoinComponent {
    private val repo: ViewedPropertiesRepository by inject()
    private val userRepositoryImpl: UserRepository by inject()

    fun getViewedProperties(userId: String): ViewedProperties? {
        val user = userRepositoryImpl.getById(userId)
        return repo.getAllByUserId(user._id)
    }

    fun markAsViewed(userId: String, propertyId: String): ViewedPropertiesResponse {
        val markedAsViewed = repo.markAsViewed(userId = userId, propertyId = propertyId)
        return ViewedPropertiesResponse(propertyId = propertyId, errorMessage = markedAsViewed.first?.message)
    }
}