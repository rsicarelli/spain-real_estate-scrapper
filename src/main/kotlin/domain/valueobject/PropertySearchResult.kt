package domain.entity

data class PropertySearchResult(
    val items: List<PropertyItem>,
    val pagination: Pagination
)

data class PropertyItem(
    val reference: String,
    val price: Double,
    val title: String,
    val location: String,
    val surface: Int,
    val dormCount: Int,
    val description: String,
    val bathCount: Int,
    val imageUrl: String,
    val tag: String,
    val propertyUrl: String
)

data class Pagination(
    val totalItems: Int,
    val pagesUrl: List<String>
)