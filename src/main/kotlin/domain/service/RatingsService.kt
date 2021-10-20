package me.rsicarelli.domain.service

import me.rsicarelli.domain.entity.Ratings
import me.rsicarelli.domain.repository.RatingsRepository
import me.rsicarelli.domain.repository.UserRepository
import me.rsicarelli.domain.valueobject.RatingResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RatingsService : KoinComponent {
    private val repo: RatingsRepository by inject()
    private val userRepositoryImpl: UserRepository by inject()

    fun getRatings(userId: String): Ratings? {
        val user = userRepositoryImpl.getById(userId)
        return repo.getAllByUserId(user._id)
    }

    fun toggleRatings(userId: String, isUpVoted: Boolean, propertyId: String): RatingResponse {
        val ratings =
            repo.toggleRating(userId = userId, isUpVoted = isUpVoted, propertyId = propertyId)
        return RatingResponse(propertyId = ratings)
    }
}