package me.rsicarelli.data.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import domain.entity.User
import me.rsicarelli.domain.entity.ViewedProperties
import me.rsicarelli.domain.service.ViewedPropertiesService
import me.rsicarelli.domain.valueobject.ViewedPropertyInput

fun SchemaBuilder.viewedPropertiesSchema(viewedPropertiesService: ViewedPropertiesService) {
    inputType<ViewedPropertyInput> {
        description = "The input of the viewed"
    }

    type<ViewedProperties> {
        description = "Viewed properties object"
    }

    query("getViewedProperties") {
        resolver { ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")
                viewedPropertiesService.getViewedProperties(userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("markAsViewed") {
        description = "Mark property as viewed"
        resolver { viewedPropertyInput: ViewedPropertyInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")
                viewedPropertiesService.markAsViewed(
                    userId = userId,
                    propertyId = viewedPropertyInput.propertyId
                )
            } catch (e: Exception) {
                null
            }
        }
    }
}