package me.rsicarelli.domain.repository

import me.rsicarelli.data.repository.Repository
import me.rsicarelli.domain.entity.Favourites

interface FavouritesRepository : Repository<Favourites> {
    fun toggleFavourite(isFavourited: Boolean, propertyId: String, userId: String): String
    fun getAllByUserId(userId: String): Favourites?
}