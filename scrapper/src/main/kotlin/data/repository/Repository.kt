package me.rsicarelli.data.repository

import com.mongodb.client.MongoCollection
import domain.entity.Model
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.save
import org.litote.kmongo.updateOne

interface Repository<T : Model> {
    var col: MongoCollection<T>

    fun getById(id: String): T {
        return try {
            col.findOne(Model::_id eq id)
                ?: throw Exception("No item with that ID exists")
        } catch (t: Throwable) {
            throw Exception("Cannot get item")
        }
    }

    fun getAll(): List<T> {
        return try {
            val res = col.find()
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get all items")
        }
    }

    fun add(entry: T): T {
        return try {
            col.save(entry)
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot add item")
        }
    }

}