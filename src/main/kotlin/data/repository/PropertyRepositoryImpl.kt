package data.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.InsertOneModel
import com.mongodb.client.model.ReplaceOneModel
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.client.model.WriteModel
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
import org.bson.Document
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

    override suspend fun deleteAll(removed: List<String>): Flow<Unit> {
        return flow {
            col.deleteMany(Property::_id `in` removed)
            emit(Unit)
        }
    }

    override fun getByIds(ids: List<String>): List<Property> {
        return col.find(Property::_id `in` ids).toList()
    }

    override fun addAll(propertiesToSave: List<Property>): List<Property> {
        return try {
            val writes: MutableList<WriteModel<Property>> = mutableListOf()

            val cachedProperties = col.find(Property::_id `in` propertiesToSave.map { it._id }).toList()
            cachedProperties.forEach { cachedProperty ->
                propertiesToSave.find { it._id == cachedProperty._id }?.let {
                    writes.add(
                        ReplaceOneModel(
                            Document("_id", it._id),
                            it.copy(createdAt = cachedProperty.createdAt),
                            ReplaceOptions().upsert(true)
                        )
                    )
                }
            }

            propertiesToSave.filterNot { property -> cachedProperties.any { it._id == property._id } }
                .map { InsertOneModel(it) }
                .forEach { writes.add(it) }

            col.bulkWrite(writes)

            propertiesToSave
        } catch (t: Throwable) {
            throw Exception("Cannot add items")
        }
    }
}