package me.rsicarelli.data.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import me.rsicarelli.domain.entity.Ratings
import me.rsicarelli.domain.repository.RatingsRepository
import org.litote.kmongo.*
import java.util.*

class RatingsRepositoryImpl(client: MongoClient) : RatingsRepository {

    override lateinit var col: MongoCollection<Ratings>

    init {
        val database = client.getDatabase("home_hunt")
        col = database.getCollection<Ratings>("UserRatings")
    }

    override fun toggleRating(isUpVoted: Boolean, propertyId: String, userId: String): String {
        try {
            col.findOne(Ratings::userId eq userId)
                ?.let {
                    val (upVotedProperties, downVotedProperties) = handleUpAndDownVotedProperties(
                        isUpVoted = isUpVoted, currentRatings = it, propertyId = propertyId
                    )
                    col.updateOne(
                        Ratings::userId eq userId,
                        it.copy(upVotedProperties = upVotedProperties, downVotedProperties = downVotedProperties)
                    )
                } ?: col.save(
                Ratings(
                    _id = UUID.randomUUID().toString(),
                    userId = userId,
                    upVotedProperties = if (isUpVoted) listOf(propertyId) else emptyList(),
                    downVotedProperties = if (!isUpVoted) listOf(propertyId) else emptyList()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return propertyId
    }

    private fun handleUpAndDownVotedProperties(
        isUpVoted: Boolean,
        currentRatings: Ratings,
        propertyId: String
    ): Pair<List<String>, List<String>> {
        val upVotedProperties = mutableListOf<String>().apply { addAll(currentRatings.upVotedProperties) }
        val downVotedProperties = mutableListOf<String>().apply { addAll(currentRatings.downVotedProperties) }

        when {
            isUpVoted -> {
                if (!upVotedProperties.contains(propertyId)) {
                    upVotedProperties.add(propertyId)
                } else {
                    upVotedProperties.remove(propertyId)
                }

                runCatching { downVotedProperties.remove(propertyId) }
            }
            !isUpVoted && !downVotedProperties.contains(propertyId) -> {
                downVotedProperties.add(propertyId)
                runCatching { upVotedProperties.remove(propertyId) }
            }
        }
        return Pair(upVotedProperties, downVotedProperties)
    }

    override fun getAllByUserId(userId: String): Ratings? {
        return col.findOne(Ratings::userId eq userId)
    }
}