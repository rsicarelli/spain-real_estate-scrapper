package domain.valueobjects

data class PropertySearchResult(
    val reference: String,
    val price: Double,
    val title: String,
    val location: String,
    val surface: String?,
    val dormCount: Int?,
    val description: String,
    val bathCount: Int?,
    val imageUrl: String,
    val tag: String?,
    val propertyUrl: String
)