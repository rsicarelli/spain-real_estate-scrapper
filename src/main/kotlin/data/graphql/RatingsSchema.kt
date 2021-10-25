package me.rsicarelli.data.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import domain.entity.User
import me.rsicarelli.domain.entity.Ratings
import me.rsicarelli.domain.service.RatingsService
import me.rsicarelli.domain.valueobject.DownVoteInput
import me.rsicarelli.domain.valueobject.UpVoteInput

fun SchemaBuilder.ratingsSchema(ratingsService: RatingsService) {
    inputType<UpVoteInput> {
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

    mutation("upVote") {
        description = "Up vote a property"
        resolver { upVoteInput: UpVoteInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")
                ratingsService.toggleRatings(
                    userId = userId,
                    isUpVoted = true,
                    propertyId = upVoteInput.propertyId
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("downVote") {
        description = "Down vote a property"
        resolver { downVoteInput: DownVoteInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")
                ratingsService.toggleRatings(
                    userId = userId,
                    isUpVoted = false,
                    propertyId = downVoteInput.propertyId
                )
            } catch (e: Exception) {
                null
            }
        }
    }
}