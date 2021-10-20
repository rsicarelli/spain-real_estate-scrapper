package me.rsicarelli.domain.valueobject

data class RatingInput(val isUpVoted: Boolean, val propertyId: String)

data class RatingResponse(val propertyId: String)