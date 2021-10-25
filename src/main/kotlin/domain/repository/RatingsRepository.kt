package me.rsicarelli.domain.repository

import me.rsicarelli.data.repository.Repository
import me.rsicarelli.domain.entity.Ratings

interface RatingsRepository : Repository<Ratings> {
    fun toggleRating(isUpVoted: Boolean, propertyId: String, userId: String): Ratings?
    fun getAllByUserId(userId: String): Ratings?
}