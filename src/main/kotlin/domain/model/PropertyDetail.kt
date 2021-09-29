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