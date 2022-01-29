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

    override fun toggleRating(isUpVoted: Boolean, propertyId: String, userId: String): Ratings? {
        var ratings: Ratings? = null
        try {
            col.findOne(Ratings::userId eq userId)?.let { currentRatings ->
                val (upVoted, downVoted) = handleUpAndDownVotedProperties(isUpVoted, currentRatings, propertyId)
                col.updateOne(
                    filter = Ratings::userId eq userId,
                    target = currentRatings.copy(upVotedProperties = upVoted, downVotedProperties = downVoted)
                        .also { ratings = it })
            } ?: col.save(
                Ratings(
                    _id = UUID.randomUUID().toString(),
                    userId = userId,
                    upVotedProperties = if (isUpVoted) listOf(propertyId) else emptyList(),
                    downVotedProperties = if (!isUpVoted) listOf(propertyId) else emptyList()
                ).also { ratings = it }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ratings
    }

    private fun handleUpAndDownVotedProperties(
        isUpVoted: Boolean,
        currentRatings: Ratings,
        propertyId: String
    ): Pair<List<String>, List<String>> {
        val upVotedProperties = currentRatings.upVotedProperties.toMutableList()
        val downVotedProperties = currentRatings.downVotedProperties.toMutableList()

        when {
            isUpVoted -> {
                upVotedProperties.takeIf { !upVotedProperties.contains(propertyId) }
                    ?.let { upVotedProperties.add(propertyId) }
                    ?: upVotedProperties.remove(propertyId)

                downVotedProperties.remove(propertyId)
            }
            !isUpVoted && !downVotedProperties.contains(propertyId) -> {
                downVotedProperties.add(propertyId)
                    .also { upVotedProperties.remove(propertyId) }
            }
        }
        return Pair(upVotedProperties, downVotedProperties)
    }

    override fun getAllByUserId(userId: String): Ratings? {
        return col.findOne(Ratings::userId eq userId)
    }
}