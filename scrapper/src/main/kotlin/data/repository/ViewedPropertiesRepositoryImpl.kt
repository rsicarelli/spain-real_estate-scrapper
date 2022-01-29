package me.rsicarelli.data.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import me.rsicarelli.domain.entity.ViewedProperties
import me.rsicarelli.domain.repository.ViewedPropertiesRepository
import org.litote.kmongo.*
import java.util.*

class ViewedPropertiesRepositoryImpl(client: MongoClient) : ViewedPropertiesRepository {

    override lateinit var col: MongoCollection<ViewedProperties>

    init {
        val database = client.getDatabase("home_hunt")
        col = database.getCollection<ViewedProperties>("UserViewedProperties")
    }

    override fun markAsViewed(propertyId: String, userId: String): Pair<Exception?, Boolean> {
        return try {
            col.findOne(ViewedProperties::userId eq userId)
                ?.let {
                    val viewedProperties = if (it.propertyIds.contains(propertyId))
                        it.propertyIds
                    else
                        it.propertyIds + listOf(propertyId)

                    col.updateOne(ViewedProperties::userId eq userId, it.copy(propertyIds = viewedProperties))
                } ?: col.save(
                ViewedProperties(
                    _id = UUID.randomUUID().toString(),
                    userId = userId,
                    propertyIds = listOf(propertyId)
                )
            )
            Pair(null, true)
        } catch (e: Exception) {
            Pair(e, false)
        }
    }

    override fun getAllByUserId(userId: String): ViewedProperties? {
        return col.findOne(ViewedProperties::userId eq userId)
    }
}