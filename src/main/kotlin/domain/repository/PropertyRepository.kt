package domain.repository

import domain.entity.Property
import domain.entity.Property.Type
import domain.entity.PropertySearchResult
import domain.valueobject.PropertyDetail
import kotlinx.coroutines.flow.Flow
import me.rsicarelli.data.repository.Repository

interface PropertyRepository : Repository<Property> {
    suspend fun scrapSearchPage(url: String, type: Type): Flow<PropertySearchResult>
    suspend fun scrapPropertyDetails(url: String, type: Type): Flow<PropertyDetail>
    suspend fun getAllFromType(type: Type): Flow<List<Property>>
    suspend fun deleteAll(removed: List<String>): Flow<Unit>
    fun getByIds(ids: List<String>): List<Property>
    fun addAll(propertiesToSave: List<Property>): List<Property>
}