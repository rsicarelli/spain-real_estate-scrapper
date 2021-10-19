package me.rsicarelli.domain.valueobject

data class FavouriteInput(val isFavourited: Boolean, val propertyId: String)

data class FavouriteResponse(val propertyId: String)