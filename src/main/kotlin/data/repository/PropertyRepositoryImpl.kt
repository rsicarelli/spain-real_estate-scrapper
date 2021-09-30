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
import kotlinx.coroutines.flow.*

class PropertyRepositoryImpl(
    private val webDataSource: WebDataSource,
    private val firebaseDataSource: FirestoreDataSource,
    private val parserProxy: ParserProxy
) : PropertyRepository {
    override suspend fun scrapSearchPage(url: String, type: Type): Flow<PropertySearchResult> =
        webDataSource.get(url).map { parserProxy.parseSearchResult(it, type) }

    override suspend fun scrapPropertyDetails(url: String, type: Type): Flow<PropertyDetail> =
        webDataSource.get(url).map { parserProxy.parsePropertyDetail(it, type) }

    override suspend fun save(properties: List<Property>, type: Type): Flow<List<Property>> =
        firebaseDataSource.addAll(properties, type)

    override suspend fun getAll(type: Type): Flow<List<Property>> = firebaseDataSource.getAll(type)

    override suspend fun markAvailability(removed: List<String>, active: List<String>, type: Type): Flow<Unit> =
        firebaseDataSource.markAvailability(removed, active, type)

}