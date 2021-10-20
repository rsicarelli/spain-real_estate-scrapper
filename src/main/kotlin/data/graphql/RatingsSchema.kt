package me.rsicarelli.data.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import domain.entity.User
import me.rsicarelli.domain.entity.Ratings
import me.rsicarelli.domain.service.RatingsService
import me.rsicarelli.domain.valueobject.RatingInput

fun SchemaBuilder.ratingsSchema(ratingsService: RatingsService) {
    inputType<RatingInput> {
        description = "The input of the rating"
    }

    type<Ratings> {
        description = "Rating object"
    }

    query("getRatings") {
        resolver { ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")
                ratingsService.getRatings(userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("toggleRating") {
        description = "Toggle rating"
        resolver { ratingInput: RatingInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")
                ratingsService.toggleRatings(
                    userId = userId,
                    isUpVoted = ratingInput.isUpVoted,
                    propertyId = ratingInput.propertyId
                )
            } catch (e: Exception) {
                null
            }
        }
    }
}