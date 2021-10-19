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

    fun delete(id: String): Boolean {
        return try {
            col.findOneAndDelete(Model::_id eq id)
                ?: throw Exception("No item with that ID exists")
            true
        } catch (t: Throwable) {
            throw Exception("Cannot delete item")
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

    fun addAll(entry: List<T>): List<T> {
        return try {
            entry.forEach {
                col.save(it)
            }
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot add items")
        }
    }

    fun update(entry: Model): Model {
        return try {
            col.updateOne(
                Model::_id eq entry._id,
                entry,
                updateOnlyNotNullProperties = true
            )
            col.findOne(Model::_id eq entry._id)
                ?: throw Exception("No item with that ID exists")
        } catch (t: Throwable) {
            throw Exception("Cannot update item")
        }
    }
}