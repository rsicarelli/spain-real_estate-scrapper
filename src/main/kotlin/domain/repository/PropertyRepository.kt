package domain.repository

import domain.entity.Location
import domain.entity.Property
import domain.entity.Property.Type
import domain.entity.PropertySearchResult
import domain.valueobject.PropertyDetail
import kotlinx.coroutines.flow.Flow

interface PropertyRepository : Repository<Property> {
    suspend fun scrapSearchPage(url: String, type: Type): Flow<PropertySearchResult>
    suspend fun scrapPropertyDetails(url: String, type: Type): Flow<PropertyDetail>
    suspend fun save(properties: List<Property>, type: Type): Flow<List<Property>>
    suspend fun getAllFromType(type: Type): Flow<List<Property>>
    suspend fun markAvailability(removed: List<String>, active: List<String>, type: Type): Flow<Unit>
    suspend fun saveUnknownLocations(locations: List<Location>): Flow<Unit>
}