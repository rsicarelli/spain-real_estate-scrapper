package me.rsicarelli.domain.repository

import me.rsicarelli.data.repository.Repository
import me.rsicarelli.domain.entity.ViewedProperties

interface ViewedPropertiesRepository : Repository<ViewedProperties> {
    fun markAsViewed(propertyId: String, userId: String): Pair<Exception?, Boolean>
    fun getAllByUserId(userId: String): ViewedProperties?
}