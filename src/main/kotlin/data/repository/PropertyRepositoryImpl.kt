package data.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions
import data.datasource.WebDataSource
import data.parser.ParserProxy
import domain.entity.Property
import domain.entity.Property.Type
import domain.entity.PropertySearchResult
import domain.repository.PropertyRepository
import domain.valueobject.PropertyDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.litote.kmongo.*

class PropertyRepositoryImpl(
    client: MongoClient,
    private val webDataSource: WebDataSource,
    private val parserProxy: ParserProxy
) : PropertyRepository {

    override lateinit var col: MongoCollection<Property>

    init {
        val database = client.getDatabase("home_hunt")
        col = database.getCollection<Property>("Property")
    }

    override suspend fun scrapSearchPage(url: String, type: Type): Flow<PropertySearchResult> =
        webDataSource.get(url).map { parserProxy.parseSearchResult(it, type) }

    override suspend fun scrapPropertyDetails(url: String, type: Type): Flow<PropertyDetail> =
        webDataSource.get(url).map { parserProxy.parsePropertyDetail(it, type) }

    override suspend fun getAllFromType(type: Type): Flow<List<Property>> {
        return flow {
            val properties = col.find("{origin:'${type.tag}',isActive: true}").toList()
            emit(properties)
        }
    }

    override fun getAllActive(): List<Property> {
        return col.find(Property::isActive eq true).toList()
    }

    override suspend fun markAvailability(removed: List<String>): Flow<Unit> {
        return flow {
            removed.forEach {
                col.findOneAndUpdate(Property::_id eq it, setValue(Property::isActive, false))
            }
        }
    }

    override fun getByIds(ids: List<String>): List<Property> {
        return col.find(Property::_id `in` ids).toList()
    }

    override fun addAll(properties: List<Property>): List<Property> {
        return try {
            val cachedProperties = col.find(Property::_id `in` properties.map { it._id }).toList()
            cachedProperties.forEach { cachedProperty ->
                properties.find { it._id == cachedProperty._id }?.let {
                    col.replaceOne(
                        Property::_id eq cachedProperty._id,
                        it.copy(createdAt = cachedProperty.createdAt),
                        ReplaceOptions().upsert(true)
                    )
                }
            }

            properties.filterNot { property ->
                cachedProperties.any { it._id == property._id }
            } .forEach {
                col.save(it)
            }
            properties
        } catch (t: Throwable) {
            throw Exception("Cannot add items")
        }
    }
}