package me.rsicarelli.domain.entity

import domain.entity.Model

data class Favourites(override val _id: String, val userId: String, val propertyIds: List<String>) : Model
