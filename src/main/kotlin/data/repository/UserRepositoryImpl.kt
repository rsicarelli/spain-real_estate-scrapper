package me.rsicarelli.data.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import domain.entity.User
import me.rsicarelli.domain.repository.UserRepository
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class UserRepositoryImpl(client: MongoClient) : UserRepository {
    override lateinit var col: MongoCollection<User>

    init {
        val database = client.getDatabase("home_hunt")
        col = database.getCollection<User>("User")
    }

    override fun getUserByEmail(email: String?): User? {
        return try {
            col.findOne(
                User::email eq email,
            )
        } catch (t: Throwable) {
            throw Exception("Cannot get user with that email")
        }
    }
}