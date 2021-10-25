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
        resolver { page: Int?, size: Int?, ctx: Context ->
            try {
                ctx.get<User>()?._id ?: error("Not signed in")

                propertyService.getPropertiesPage(page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }

}