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

val defaultPropertyMap: () -> Map<String, Any?> = {
    with(defaultProperty) {
        return@with mapOf(
            Mapper.REFERENCE to _id,
            Mapper.PRICE to price,
            Mapper.TITLE to title,
            Mapper.LOCATION to location,
            Mapper.SURFACE to surface,
            Mapper.DORM_COUNT to dormCount?.toLong(),
            Mapper.DESCRIPTION to description,
            Mapper.BATH_COUNT to bathCount?.toLong(),
            Mapper.AVATAR_URL to avatarUrl,
            Mapper.TAG to tag,
            Mapper.PROPERTY_URL to propertyUrl,
            Mapper.VIDEO_URL to videoUrl,
            Mapper.FULL_DESCRIPTION to fullDescription,
            Mapper.CHARACTERISTICS to characteristics,
            Mapper.PHOTO_GALLERY_URLS to photoGalleryUrls,
            Mapper.PDF_URL to pdfUrl,
            Mapper.LOCATION_DESCRIPTION to locationDescription
        )
    }
}

val newProperty = defaultProperty.copy(tag = "NEW")
val rentedProperty = defaultProperty.copy(tag = "RENTED")
val reservedProperty = defaultProperty.copy(tag = "RESERVED")