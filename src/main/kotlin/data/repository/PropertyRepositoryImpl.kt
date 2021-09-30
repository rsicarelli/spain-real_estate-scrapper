package data.repository

import data.datasource.FirestoreDataSource
import data.datasource.WebDataSource
import data.parser.ParserProxy
import domain.model.Property
import domain.model.Property.Type
import domain.repository.PropertyRepository
import domain.model.PropertyDetail
import domain.model.PropertySearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PropertyRepositoryImpl(
    private val webDataSource: WebDataSource,
    private val firebaseDataSource: FirestoreDataSource,
    private val parserProxy: ParserProxy
) : PropertyRepository {
    override suspend fun scrapSearchPage(
        url: String,
        type: Type
    ): Flow<PropertySearchResult> {
        return flow {
            val value = webDataSource.get(url) { result ->
                parserProxy.parseSearchResult(result, type)
            }
            emit(value)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun scrapPropertyDetails(url: String, type: Type): Flow<PropertyDetail> {
        return flow {
            emit(webDataSource.get(url) {
                parserProxy.parsePropertyDetail(it, type)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun save(properties: List<Property>, type: Type) =
        firebaseDataSource.addAll(properties, type)

    override fun getAll(type: Type) = firebaseDataSource.getAll(type)

    override fun markAvailability(removed: List<String>, active: List<String>, type: Type) =
        firebaseDataSource.markAvailability(removed, active, type)

}