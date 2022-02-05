package data.repository

import com.google.gson.JsonObject
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.InsertOneModel
import com.mongodb.client.model.ReplaceOneModel
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.client.model.WriteModel
import data.datasource.WebDataSource
import data.network.alameda10.Alameda10Response
import data.parser.ParserProxy
import domain.entity.Property
import domain.entity.Property.Type
import domain.entity.PropertySearchResult
import domain.repository.PropertyRepository
import domain.valueobject.PagingInfo
import domain.valueobject.PropertiesPage
import domain.valueobject.PropertyDetail
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.rsicarelli.data.datasource.RemoteDataSource
import org.bson.Document
import org.litote.kmongo.`in`
import org.litote.kmongo.find
import org.litote.kmongo.getCollection


class PropertyRepositoryImpl(
    client: MongoClient,
    private val webDataSource: WebDataSource,
    private val remoteDataSource: RemoteDataSource,
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

    override suspend fun getProperties(url: String, headers: Map<String, String>, body: JsonObject): List<Property> {
        return remoteDataSource.client.post<Alameda10Response>(url) {
            headers {
                headers.forEach { (key, value) ->
                    append(key, value)
                }
            }
            contentType(ContentType.Application.Json)
            this.body = body
        }.toPropertyList()
    }

    override suspend fun getAllFromType(type: Type): Flow<List<Property>> {
        return flow {
            val properties = col.find("{origin:'${type.tag}',isActive: true}").toList()
            emit(properties)
        }
    }

    override fun getPropertiesPage(page: Int, size: Int): PropertiesPage {
        try {
            val skips = page * size
            val res = col.find().skip(skips).limit(size)
            val results = res.asIterable().map { it }

            val totalDesserts = col.estimatedDocumentCount()
            val totalPages = (totalDesserts / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null

            val info = PagingInfo(totalDesserts.toInt(), totalPages.toInt(), next, prev)
            return PropertiesPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get desserts page")
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