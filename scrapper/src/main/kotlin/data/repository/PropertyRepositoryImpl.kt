package data.repository

import app.epochToDate
import com.google.gson.*
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.InsertOneModel
import com.mongodb.client.model.ReplaceOneModel
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.client.model.WriteModel
import data.datasource.WebDataSource
import data.network.alameda10.Alameda10
import data.parser.ParserProxy
import domain.entity.Location
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
import java.util.*


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


    override suspend fun getProperties(url: String, headers: Map<String, String>): List<Property> {
        val bodyRaw =
            "{\"currentPage\":0,\"itemsPerPage\":20,\"order\":\"desc\",\"orderfield\":\"creationDate\",\"ids\":[],\"UserContactId\":null,\"showAddress\":1,\"adOperationId\":\"2\",\"adScopeId\":null,\"adTypologyId\":\"0\",\"priceMin\":null,\"priceMax\":null,\"CreationDateMin\":null,\"CreationDateMax\":null,\"locationId\":[],\"drawShapePath\":null,\"homes\":null,\"chalets\":null,\"countryhouses\":null,\"isDuplex\":null,\"isPenthouse\":null,\"isStudio\":null,\"isIndependentHouse\":null,\"isSemidetachedHouse\":null,\"isTerracedHouse\":null,\"constructedAreaMin\":null,\"constructedAreaMax\":null,\"rooms_0\":null,\"rooms_1\":null,\"rooms_2\":null,\"rooms_3\":null,\"rooms_4\":null,\"baths_1\":null,\"baths_2\":null,\"baths_3\":null,\"builtTypeId\":null,\"isTopFloor\":null,\"isIntermediateFloor\":null,\"isGroundFloor\":null,\"isFirstFloor\":null,\"hasAirConditioning\":null,\"hasWardrobe\":null,\"hasGarage\":null,\"hasLift\":null,\"hasTerrace\":null,\"hasBoxRoom\":null,\"hasSwimmingPool\":null,\"hasGarden\":null,\"flatLocationId\":null,\"hasKitchen\":null,\"hasAutomaticDoor\":null,\"hasPersonalSecurity\":null,\"HasSecurity24h\":null,\"garageCapacityId\":null,\"hasHotWater\":null,\"hasExterior\":null,\"hasSuspendedFloor\":null,\"hasHeating\":null,\"isFurnish\":null,\"isBankOwned\":null,\"distributionId\":null,\"isOnlyOfficeBuilding\":null,\"ubicationId\":null,\"warehouseType_1\":null,\"warehouseType_2\":null,\"isATransfer\":null,\"isCornerLocated\":null,\"hasSmokeExtractor\":null,\"landType_1\":null,\"landType_2\":null,\"landType_3\":null,\"HasAllDayAccess\":null,\"HasLoadingDockAccess\":null,\"HasTenant\":null,\"addressVisible\":null,\"mlsIncluded\":null,\"freeText\":null,\"RefereceText\":null,\"isLowered\":null,\"priceDropDateFrom\":0,\"priceDropDateTo\":0,\"arePetsAllowed\":null,\"Equipment\":null,\"OperationStatus\":null,\"AdContract\":null,\"IsRent\":true,\"IsSale\":false,\"IsAuction\":false,\"AdState\":null}"

        return remoteDataSource.client.post<Alameda10>(url) {
            headers {
                headers.forEach { (key, value) ->
                    append(key, value)
                }
            }
            contentType(ContentType.Application.Json)
            body = JsonParser.parseString(bodyRaw).asJsonObject
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

    val customGson: Gson = GsonBuilder().registerTypeHierarchyAdapter(
        ByteArray::class.java,
        ByteArrayToBase64TypeAdapter()
    ).create()

    private class ByteArrayToBase64TypeAdapter : JsonSerializer<ByteArray?>, JsonDeserializer<ByteArray?> {

        override fun deserialize(
            json: JsonElement?,
            typeOfT: java.lang.reflect.Type?,
            context: JsonDeserializationContext?
        ): ByteArray? {
            return Base64.getDecoder().decode(json!!.asString)
        }

        override fun serialize(
            src: ByteArray?,
            typeOfSrc: java.lang.reflect.Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(Base64.getEncoder().encodeToString(src))
        }
    }
}