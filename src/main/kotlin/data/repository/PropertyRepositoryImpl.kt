package data.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import data.datasource.FirestoreDataSource
import data.datasource.WebDataSource
import data.parser.ParserProxy
import domain.entity.Location
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

    override suspend fun markAvailability(removed: List<String>): Flow<Unit> {
        return flow {
            removed.forEach {
                col.findOneAndUpdate(Property::_id eq it, setValue(Property::isActive, false))
            }
        }
    }

}