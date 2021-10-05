package utils.fixtures

import domain.model.*
import utils.fixtures.EngelFixture.Fixtures.defaultSearchResults

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
    reference = "a reference",
    price = 100.0,
    title = "a title",
    location = "a location",
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
    lat = 4.0,
    lng = 2.0,
    pdfUrl = "https://apdf.com",
    origin = "",
    viewedBy = emptyList(),
    favouriteBy = emptyList()
)

val defaultPropertyMap: () -> Map<String, Any?> = {
    with(defaultProperty) {
        return@with mapOf(
            Mapper.REFERENCE to reference,
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
            Mapper.LAT to lat,
            Mapper.LNG to lng,
            Mapper.PDF_URL to pdfUrl,
            Mapper.LOCATION_DESCRIPTION to locationDescription
        )
    }
}

val newProperty = defaultProperty.copy(tag = "NEW")
val rentedProperty = defaultProperty.copy(tag = "RENTED")
val reservedProperty = defaultProperty.copy(tag = "RESERVED")