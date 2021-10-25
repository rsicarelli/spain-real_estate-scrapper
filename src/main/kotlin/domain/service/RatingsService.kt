package me.rsicarelli.domain.service

import me.rsicarelli.domain.repository.RatingsRepository
import me.rsicarelli.domain.repository.UserRepository
import me.rsicarelli.domain.valueobject.RatingResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RatingsService : KoinComponent {
    private val repo: RatingsRepository by inject()
    private val userRepositoryImpl: UserRepository by inject()

    fun getRatings(userId: String): RatingResponse {
        val user = userRepositoryImpl.getById(userId)
        val ratings = repo.getAllByUserId(user._id)
        return RatingResponse(
            upVoted = ratings?.upVotedProperties ?: emptyList(),
            downVoted = ratings?.downVotedProperties ?: emptyList()
        )
    }

    //TODO: errors should be better handled
    fun toggleRatings(userId: String, isUpVoted: Boolean, propertyId: String): RatingResponse {
        val updatedRatings = repo.toggleRating(userId = userId, isUpVoted = isUpVoted, propertyId = propertyId)
        return RatingResponse(
            upVoted = updatedRatings?.upVotedProperties ?: emptyList(),
            downVoted = updatedRatings?.downVotedProperties ?: emptyList()
        )
    }
}