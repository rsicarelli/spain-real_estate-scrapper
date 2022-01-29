package data.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import domain.entity.Property
import domain.entity.User
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
        description = "Get all properties, filtering out down voted ones"
        resolver { ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")

                propertyService.getProperties(userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("recommendedProperties") {
        description = "Get recommended properties, filtering out up and down voted"

        resolver { ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")

                propertyService.getRecommendations(userId)
            } catch (e: Exception) {
                null
            }
        }
    }

}