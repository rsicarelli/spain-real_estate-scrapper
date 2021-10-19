package data.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import data.datasource.FirestoreDataSource
import data.datasource.WebDataSource
import data.parser.ParserProxy
import domain.entity.Location
import domain.entity.Model
import domain.entity.Property
import domain.entity.Property.Type
import domain.entity.PropertySearchResult
import domain.repository.PropertyRepository
import domain.repository.Repository
import domain.valueobject.PropertyDetail
import kotlinx.coroutines.flow.Flow
import org.litote.kmongo.*
import kotlinx.coroutines.flow.map

class PropertyRepositoryImpl(
    private val client: MongoClient,
    private val webDataSource: WebDataSource,
    private val firebaseDataSource: FirestoreDataSource,
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

    override suspend fun save(properties: List<Property>, type: Type): Flow<List<Property>> =
        firebaseDataSource.addAll(properties, type)

    override suspend fun getAllFromType(type: Type): Flow<List<Property>> = firebaseDataSource.getAllFromType(type)

    override suspend fun markAvailability(removed: List<String>, active: List<String>, type: Type): Flow<Unit> =
        firebaseDataSource.markAvailability(removed, active, type)

    override suspend fun saveUnknownLocations(locations: List<Location>): Flow<Unit> =
        firebaseDataSource.saveUnknownLocations(locations)

}