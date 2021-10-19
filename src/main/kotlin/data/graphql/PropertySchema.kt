package data.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import domain.entity.Property
import domain.service.PropertyService

fun SchemaBuilder.propertySchema(propertyService: PropertyService) {

    type<Property> {
        description = "Property object"
    }

    query("property") {
        resolver { propertyId: String ->
            try {
                propertyService.getProperty(propertyId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("properties") {
        resolver { ->
            try {
                propertyService.getProperties()
            } catch (e: Exception) {
                null
            }
        }
    }

}