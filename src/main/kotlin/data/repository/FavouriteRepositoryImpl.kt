package me.rsicarelli.data.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import domain.entity.User
import me.rsicarelli.domain.entity.Favourites
import me.rsicarelli.domain.repository.FavouritesRepository
import org.litote.kmongo.*
import java.util.*

class FavouriteRepositoryImpl(client: MongoClient) : FavouritesRepository {

    override lateinit var col: MongoCollection<Favourites>

    init {
        val database = client.getDatabase("home_hunt")
        col = database.getCollection<Favourites>("UserFavourites")
    }

    override fun toggleFavourite(isFavourited: Boolean, propertyId: String, userId: String): String {
        try {
            col.findOne(Favourites::userId eq userId)
                ?.let {
                    val favourites = if (isFavourited) {
                        it.propertyIds + listOf(propertyId)
                    } else {
                        it.propertyIds.filter { id -> id != propertyId }
                    }
                    col.updateOne(Favourites::userId eq userId, it.copy(propertyIds = favourites))
                } ?: col.save(
                Favourites(
                    _id = UUID.randomUUID().toString(),
                    userId = userId,
                    propertyIds = listOf(propertyId)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return propertyId
    }

    override fun getAllByUserId(userId: String): Favourites? {
        return col.findOne(Favourites::userId eq userId)
    }
}