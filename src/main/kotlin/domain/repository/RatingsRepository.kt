package me.rsicarelli.domain.repository

import me.rsicarelli.data.repository.Repository
import me.rsicarelli.domain.entity.Ratings

interface RatingsRepository : Repository<Ratings> {
    fun toggleRating(isUpVoted: Boolean, propertyId: String, userId: String): String
    fun getAllByUserId(userId: String): Ratings?
}