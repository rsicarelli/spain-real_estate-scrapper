package domain.valueobject

import domain.entity.Property

data class PagingInfo(var count: Int, var pages: Int, var next: Int?, var prev: Int?)

data class PropertiesPage(val results: List<Property>, val pagingInfo: PagingInfo)

data class PropertyResponse(val count: Int, val results: List<Property>)