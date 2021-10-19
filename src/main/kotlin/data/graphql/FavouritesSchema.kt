package me.rsicarelli.data.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import domain.entity.User
import me.rsicarelli.domain.entity.Favourites
import me.rsicarelli.domain.service.FavouritesService
import me.rsicarelli.domain.valueobject.FavouriteInput

fun SchemaBuilder.favouritesSchema(favouritesService: FavouritesService) {
    inputType<FavouriteInput> {
        description = "The input of the favourite"
    }

    type<Favourites> {
        description = "Favourite object"
    }

    query("getFavourites") {
        resolver { ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")
                favouritesService.getFavourites(userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("toggleFavourite") {
        description = "Toggle favourite"
        resolver { favouriteInput: FavouriteInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?._id ?: error("Not signed in")
                favouritesService.toggleFavourite(
                    userId = userId,
                    isFavourited = favouriteInput.isFavourited,
                    propertyId = favouriteInput.propertyId
                )
            } catch (e: Exception) {
                null
            }
        }
    }
}