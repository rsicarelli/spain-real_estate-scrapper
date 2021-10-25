package domain.valueobject

import domain.entity.Property

data class PropertyItem(
    val property: Property,
    val isViewed: Boolean,
    val isUpVoted: Boolean,
    val isDownVoted: Boolean,
)

data class PropertyResponse(val result: List<PropertyItem>, val totalItems: Int)