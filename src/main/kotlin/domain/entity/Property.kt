package domain.entity

import app.*
import com.google.common.annotations.VisibleForTesting

data class Property(
    override val _id: String,
    val price: Double,
    val title: String,
    val location: Location,
    val surface: Int,
    val dormCount: Int?,
    val description: String,
    val bathCount: Int?,
    val avatarUrl: String,
    val tag: String?,
    val propertyUrl: String,
    val videoUrl: String?,
    val fullDescription: String?,
    val locationDescription: String?,
    val characteristics: List<String?>,
    val photoGalleryUrls: List<String?>,
    val pdfUrl: String?,
    val origin: String,
    val viewedBy: List<String?>,
    val isFavourited: Boolean
) : Model {

    sealed class Type(val tag: String) {
        object APROPERTIES : Type("aProperties")
        object ENGELS : Type("engels")
    }

    sealed class Tag(val identifier: String) {
        object EMPTY : Tag("")
        object NEW : Tag("NEW")
        object RESERVED : Tag("RESERVED")
        object RENTED : Tag("RENTED")
    }
}

fun String?.toTag() = this?.let {
    return@let when (it.uppercase()) {
        Property.Tag.NEW.identifier -> Property.Tag.NEW.identifier
        Property.Tag.RESERVED.identifier -> Property.Tag.RESERVED.identifier
        Property.Tag.RENTED.identifier -> Property.Tag.RENTED.identifier
        else -> Property.Tag.EMPTY.identifier
    }
} ?: Property.Tag.EMPTY

fun Property.toMap(): Map<String, Any?> =
    mapOf(
        Mapper.REFERENCE to _id,
        Mapper.PRICE to price,
        Mapper.TITLE to title,
        Mapper.LOCATION to location,
        Mapper.SURFACE to surface,
        Mapper.DORM_COUNT to dormCount,
        Mapper.DESCRIPTION to description,
        Mapper.BATH_COUNT to bathCount,
        Mapper.AVATAR_URL to avatarUrl,
        Mapper.TAG to tag.toTag(),
        Mapper.PROPERTY_URL to propertyUrl,
        Mapper.VIDEO_URL to videoUrl,
        Mapper.FULL_DESCRIPTION to fullDescription,
        Mapper.CHARACTERISTICS to characteristics,
        Mapper.PHOTO_GALLERY_URLS to photoGalleryUrls,
        Mapper.PDF_URL to pdfUrl,
        Mapper.LOCATION_DESCRIPTION to locationDescription,
        Mapper.ORIGIN to origin,
        Mapper.VIEWED_BY to viewedBy,
        Mapper.IS_FAVORITED to isFavourited
    )

fun Map<String, Any?>.toProperty() =
    Property(
        _id = asString(Mapper.REFERENCE),
        price = asDouble(Mapper.PRICE),
        title = asString(Mapper.TITLE),
        location = asLocation(Mapper.LOCATION),
        surface = asInt(Mapper.SURFACE),
        dormCount = asNullableInt(Mapper.DORM_COUNT),
        description = asString(Mapper.DESCRIPTION),
        bathCount = asNullableInt(Mapper.BATH_COUNT),
        avatarUrl = asString(Mapper.AVATAR_URL),
        tag = asNullableString(Mapper.TAG, default = Property.Tag.EMPTY.identifier),
        propertyUrl = asString(Mapper.PROPERTY_URL),
        videoUrl = asNullableString(Mapper.VIDEO_URL),
        fullDescription = asNullableString(Mapper.FULL_DESCRIPTION),
        characteristics = asStringList(Mapper.CHARACTERISTICS),
        photoGalleryUrls = asStringList(Mapper.PHOTO_GALLERY_URLS),
        pdfUrl = asNullableString(Mapper.PDF_URL),
        locationDescription = asNullableString(Mapper.LOCATION_DESCRIPTION),
        origin = asString(Mapper.ORIGIN),
        viewedBy = asStringList(Mapper.VIEWED_BY),
        isFavourited = asBoolean(Mapper.IS_FAVORITED)
    )

@VisibleForTesting
object Mapper {
    const val REFERENCE = "reference"
    const val PRICE = "price"
    const val TITLE = "title"
    const val LOCATION = "location"
    const val SURFACE = "surface"
    const val DORM_COUNT = "dormCount"
    const val DESCRIPTION = "description"
    const val BATH_COUNT = "bathCount"
    const val AVATAR_URL = "avatarUrl"
    const val TAG = "tag"
    const val PROPERTY_URL = "propertyUrl"
    const val VIDEO_URL = "videoUrl"
    const val FULL_DESCRIPTION = "fullDescription"
    const val CHARACTERISTICS = "characteristics"
    const val PHOTO_GALLERY_URLS = "photoGalleryUrls"
    const val LAT = "lat"
    const val LNG = "lng"
    const val PDF_URL = "pdfUrl"
    const val LOCATION_DESCRIPTION = "locationDescription"
    const val ORIGIN = "origin"
    const val VIEWED_BY = "viewedBy"
    const val IS_FAVORITED = "isFavourited"
}