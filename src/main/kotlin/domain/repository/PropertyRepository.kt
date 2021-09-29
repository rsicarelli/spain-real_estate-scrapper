package domain.repositories

import domain.entity.Property
import domain.entity.Property.Type
import domain.valueobjects.PropertyDetail
import domain.valueobjects.PropertySearchResult
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {
    suspend fun scrapSearchPage(url: String, type: Type): Flow<PropertySearchResult>
    suspend fun scrapPropertyDetails(url: String, type: Type): Flow<PropertyDetail>
    suspend fun save(properties: List<Property>, type: Type): Flow<List<Property>>
    fun getAll(type: Type): Flow<List<Property>>
    fun markAvailability(removed: List<String>, active: List<String>, type: Type): Flow<Unit>
}