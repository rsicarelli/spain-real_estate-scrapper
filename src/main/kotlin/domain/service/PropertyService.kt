package domain.service

import domain.entity.Property
import domain.repository.PropertyRepository
import domain.valueobject.PropertiesPage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PropertyService : KoinComponent {
    private val repository: PropertyRepository by inject()

    fun getPropertiesPage(page: Int, size: Int): PropertiesPage {
        return repository.getPropertiesPage(page, size)
    }

    fun getProperty(id: String): Property {
        return repository.getById(id)
    }
}
