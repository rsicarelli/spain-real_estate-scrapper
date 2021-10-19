package me.rsicarelli.domain.service

import me.rsicarelli.domain.entity.Favourites
import me.rsicarelli.domain.repository.FavouritesRepository
import me.rsicarelli.domain.repository.UserRepository
import me.rsicarelli.domain.valueobject.FavouriteResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavouritesService : KoinComponent {
    private val repo: FavouritesRepository by inject()
    private val userRepositoryImpl: UserRepository by inject()

    fun getFavourites(userId: String): Favourites? {
        val user = userRepositoryImpl.getById(userId)
        return repo.getAllByUserId(user._id)
    }

    fun toggleFavourite(userId: String, isFavourited: Boolean, propertyId: String): FavouriteResponse {
        val favourite =
            repo.toggleFavourite(userId = userId, isFavourited = isFavourited, propertyId = propertyId)
        return FavouriteResponse(propertyId = favourite)
    }
}