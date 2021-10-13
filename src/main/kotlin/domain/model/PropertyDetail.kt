package domain.model

data class PropertyDetail(
    val reference: String,
    val videoUrl: String?,
    val fullDescription: String?,
    val locationDescription: String?,
    val characteristics: List<String?>,
    val photosGalleryUrls: List<String?>,
    val lat: Double?,
    val lng: Double?,
    val pdfUrl: String?
)

fun PropertyDetail.toProperty(propertyItem: PropertyItem, origin: Property.Type) =
    Property(
        reference = propertyItem.reference,
        price = propertyItem.price,
        title = propertyItem.title,
        location = Location.fromLatLng(propertyItem.location, lat, lng),
        surface = propertyItem.surface,
        dormCount = propertyItem.dormCount,
        description = propertyItem.description,
        bathCount = propertyItem.bathCount,
        avatarUrl = propertyItem.imageUrl,
        tag = propertyItem.tag,
        propertyUrl = propertyItem.propertyUrl,
        videoUrl = videoUrl,
        fullDescription = fullDescription,
        characteristics = characteristics,
        photoGalleryUrls = photosGalleryUrls,
        locationDescription = locationDescription,
        pdfUrl = pdfUrl,
        origin = origin.tag,
        viewedBy = emptyList(),
        isFavourited = false
    )
