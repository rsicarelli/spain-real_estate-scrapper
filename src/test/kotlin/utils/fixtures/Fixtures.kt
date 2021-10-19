package utils.fixtures

import domain.entity.*
import utils.fixtures.EngelFixture.Fixtures.defaultSearchResults
import java.util.*

val emptyPagination = Pagination(0, emptyList())

val searchResultWithNoPagination = PropertySearchResult(
    pagination = emptyPagination,
    items = defaultSearchResults.items
)

private val multipliedItems = mutableListOf<PropertyItem>().apply {
    repeat(defaultSearchResults.pagination.pagesUrl.size + 1) {
        addAll(defaultSearchResults.items)
    }
}

val searchResultsWithPagination = Pair(
    defaultSearchResults,
    multipliedItems
)

val defaultProperty = Property(
    _id = UUID.randomUUID().toString(),
    price = 100.0,
    title = "a title",
    location = Location(name = "a location", lat = 0.0, lng = 0.0, isApproximated = false, isUnknown = false),
    surface = 30,
    dormCount = 2,
    description = "a description",
    bathCount = 2,
    avatarUrl = "https://someimage.com",
    tag = "EMPTY",
    propertyUrl = "https://someurl.com",
    videoUrl = "https://somevideo.com",
    fullDescription = "a full description",
    locationDescription = "a location description",
    characteristics = listOf("foo", "bar"),
    photoGalleryUrls = listOf("https://aimage.com", "https://anotherimage.com"),
    pdfUrl = "https://apdf.com",
    origin = "",
)

val newProperty = defaultProperty.copy(tag = "NEW")
val rentedProperty = defaultProperty.copy(tag = "RENTED")
val reservedProperty = defaultProperty.copy(tag = "RESERVED")