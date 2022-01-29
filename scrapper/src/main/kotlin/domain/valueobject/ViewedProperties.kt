package me.rsicarelli.domain.valueobject

data class ViewedPropertyInput(val propertyId: String)

data class ViewedPropertiesResponse(val propertyId: String, val errorMessage: String?)