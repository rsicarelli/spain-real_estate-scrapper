package domain.repository

import domain.model.Location
import domain.model.Property
import domain.model.Property.Type
import domain.model.PropertyDetail
import domain.model.PropertySearchResult
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {
    suspend fun scrapSearchPage(url: String, type: Type): Flow<PropertySearchResult>
    suspend fun scrapPropertyDetails(url: String, type: Type): Flow<PropertyDetail>
    suspend fun save(properties: List<Property>, type: Type): Flow<List<Property>>
    suspend fun getAllFromType(type: Type): Flow<List<Property>>
    suspend fun getAll(): Flow<List<Property>>
    suspend fun markAvailability(removed: List<String>, active: List<String>, type: Type): Flow<Unit>
    suspend fun saveUnknownLocations(locations: List<Location>): Flow<Unit>
}