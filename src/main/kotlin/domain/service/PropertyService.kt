package domain.service

import com.mongodb.client.MongoClient
import data.repository.PropertyRepositoryImpl
import domain.entity.Property
import domain.repository.PropertyRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class PropertyService : KoinComponent {
    private val repository: PropertyRepository by inject()

    fun addProperties(list: List<Property>): List<Property> {
        val uid = UUID.randomUUID().toString()
        return repository.addAll(list)
    }

    fun getProperties(): List<Property> {
        return repository.getAll()
    }

    fun getProperty(id: String): Property {
        return repository.getById(id)
    }

    fun updateProperty(property: Property): Property {
        return repository.update(property)
    }
}