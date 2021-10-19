package me.rsicarelli.domain.entity

import domain.entity.Model

data class ViewedProperties(override val _id: String, val userId: String, val propertyIds: List<String>) : Model