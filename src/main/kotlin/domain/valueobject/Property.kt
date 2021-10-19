package domain.valueobject

import domain.entity.Property

data class PagingInfo(var count: Int, var pages: Int, var next: Int?, var prev: Int?)

data class PropertiesPage(val results: List<Property>, val info: PagingInfo)

data class PropertyItem(
    val property: Property,
    val isViewed: Boolean,
    val isFavourited: Boolean
)

data class PropertyResponse(val result: List<PropertyItem>, val totalItems: Int)